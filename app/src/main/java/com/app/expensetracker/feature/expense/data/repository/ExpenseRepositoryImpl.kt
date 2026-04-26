package com.app.expensetracker.feature.expense.data.repository

import com.app.expensetracker.feature.expense.data.local.ExpenseDao
import com.app.expensetracker.feature.expense.data.mapper.toDomain
import com.app.expensetracker.feature.expense.data.mapper.toDomainList
import com.app.expensetracker.feature.expense.data.mapper.toDto
import com.app.expensetracker.feature.expense.data.mapper.toEntity
import com.app.expensetracker.feature.expense.data.mapper.toEntityList
import com.app.expensetracker.feature.expense.data.model.ExpenseDto
import com.app.expensetracker.feature.expense.data.model.MonthlyBudgetDto
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.MonthlyBudget
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Offline-first implementation of ExpenseRepository.
 *
 * Strategy:
 *  - READ  → always from Room (instant, works offline)
 *  - SYNC  → Firestore snapshot listener writes into Room in background
 *  - WRITE → write to Firestore first, then cache locally in Room
 *
 * This means:
 *  1. The UI never waits for a network call — Room emits immediately.
 *  2. When Firestore delivers a snapshot (online), Room is updated
 *     and the UI re-renders automatically via Flow.
 *  3. Offline: last cached data is always visible.
 */
class ExpenseRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    // Background scope for one-shot Firestore → Room sync writes
    private val syncScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // ── Firestore references ───────────────────────────────────────────────

    private fun budgetDoc(year: Int, month: Int) =
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("budgets")
            .document("$year-${month.toString().padStart(2, '0')}")

    private fun userExpensesRef() =
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("expenses")

    // ── Write operations ───────────────────────────────────────────────────

    override suspend fun addExpense(expense: Expense) {
        // 1. Write to Firestore and get the auto-generated document ID
        val docRef = userExpensesRef().add(expense.toDto()).await()

        // 2. Cache locally with the real Firestore ID so Room and Firestore stay in sync
        expenseDao.upsert(expense.copy(id = docRef.id).toEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        // 1. Update Firestore
        userExpensesRef().document(expense.id).set(expense.toDto()).await()

        // 2. Update local cache
        expenseDao.upsert(expense.toEntity())
    }

    override suspend fun deleteExpense(expenseId: String) {
        // 1. Delete from Firestore
        userExpensesRef().document(expenseId).delete().await()

        // 2. Remove from local cache
        expenseDao.deleteById(expenseId)
    }

    // ── Read operations (Room as source of truth) ──────────────────────────

    /**
     * Returns a Flow from Room for instant, offline-capable reads.
     * In the background, attaches a Firestore listener that syncs
     * any remote changes into Room — which then re-emits via the Flow.
     */
    override fun getExpensesByMonth(year: Int, month: Int): Flow<List<Expense>> {
        // Kick off background Firestore sync for this month
        syncFirestoreMonthToRoom(year, month)

        // Return Room's Flow as the UI-facing source of truth
        return expenseDao
            .getByMonth(year, month)
            .map { it.toDomainList() }
    }

    /**
     * Recent expenses — Room first, Firestore listener syncs in background.
     */
    override fun getRecentExpenses(limit: Int): Flow<List<Expense>> {
        // Recent expenses are a subset of what's already cached,
        // no extra Firestore listener needed here.
        return expenseDao
            .getRecent(limit)
            .map { it.toDomainList() }
    }

    /**
     * Observe a single expense by ID — useful for the detail/edit screen.
     */
    override fun observeExpenseById(expenseId: String): Flow<Expense> =
        callbackFlow {
            val listener = userExpensesRef()
                .document(expenseId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) { close(error); return@addSnapshotListener }
                    if (snapshot == null || !snapshot.exists()) {
                        close(IllegalStateException("Expense not found"))
                        return@addSnapshotListener
                    }
                    val expense = snapshot
                        .toObject(ExpenseDto::class.java)
                        ?.toDomain(snapshot.id)
                    if (expense != null) {
                        // Keep local cache in sync
                        syncScope.launch { expenseDao.upsert(expense.toEntity()) }
                        trySend(expense)
                    }
                }
            awaitClose { listener.remove() }
        }

    // ── Budget operations (Firestore only — no local cache needed yet) ─────

    override fun observeMonthlyBudget(year: Int, month: Int): Flow<MonthlyBudget?> =
        callbackFlow {
            val listener = budgetDoc(year, month)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) { close(error); return@addSnapshotListener }
                    if (snapshot == null || !snapshot.exists()) {
                        trySend(null)
                        return@addSnapshotListener
                    }
                    val dto = snapshot.toObject(MonthlyBudgetDto::class.java)!!
                    trySend(
                        MonthlyBudget(
                            year = year,
                            month = month,
                            monthlyBudget = dto.monthlyBudget,
                            categoryBudgets = dto.categoryBudgets.mapKeys {
                                ExpenseCategory.fromValue(it.key)
                            }
                        )
                    )
                }
            awaitClose { listener.remove() }
        }

    override suspend fun saveMonthlyBudget(year: Int, month: Int, amount: Double) {
        budgetDoc(year, month)
            .set(
                mapOf(
                    "monthlyBudget" to amount,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                SetOptions.merge()
            ).await()
    }

    override suspend fun saveCategoryBudget(
        year: Int,
        month: Int,
        category: ExpenseCategory,
        amount: Double
    ) {
        val docRef = budgetDoc(year, month)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            val existing = snapshot.get("categoryBudgets") as? Map<String, Double> ?: emptyMap()
            val updated = existing.toMutableMap().apply { put(category.value, amount) }
            transaction.set(
                docRef,
                mapOf("categoryBudgets" to updated, "updatedAt" to FieldValue.serverTimestamp()),
                SetOptions.merge()
            )
        }.await()
    }

    // ── Private sync helper ────────────────────────────────────────────────

    /**
     * Attaches a one-shot Firestore listener for the given month.
     * Each snapshot is written into Room in a background coroutine.
     * The listener is intentionally long-lived (app process lifetime)
     * so real-time updates from other devices keep the cache fresh.
     *
     * Note: because this runs in syncScope (not viewModelScope), it
     * survives screen rotations and ViewModel recreation.
     */
    private fun syncFirestoreMonthToRoom(year: Int, month: Int) {
        syncScope.launch {
            val listenerRegistration = userExpensesRef()
                .whereEqualTo("year", year)
                .whereEqualTo("month", month)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null) return@addSnapshotListener

                    val expenses = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ExpenseDto::class.java)?.toDomain(doc.id)
                    }

                    // Write entire month snapshot into Room atomically
                    syncScope.launch {
                        expenseDao.upsertAll(expenses.toEntityList())
                    }
                }

            // Keep reference alive for the scope lifetime
            // (SupervisorJob means it won't cancel on individual failures)
        }
    }
}
