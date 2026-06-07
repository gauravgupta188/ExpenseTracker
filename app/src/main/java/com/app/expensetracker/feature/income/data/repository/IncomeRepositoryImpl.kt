package com.app.expensetracker.feature.income.data.repository

import com.app.expensetracker.core.utils.AppLogger
import com.app.expensetracker.feature.income.data.local.IncomeDao
import com.app.expensetracker.feature.income.data.mapper.toDomain
import com.app.expensetracker.feature.income.data.mapper.toDomainList
import com.app.expensetracker.feature.income.data.mapper.toDto
import com.app.expensetracker.feature.income.data.mapper.toEntity
import com.app.expensetracker.feature.income.data.mapper.toEntityList
import com.app.expensetracker.feature.income.data.model.IncomeDto
import com.app.expensetracker.feature.income.domain.model.Income
import com.app.expensetracker.feature.income.domain.repository.IncomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class IncomeRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val incomeDao: IncomeDao
) : IncomeRepository {

    companion object {
        private val TAG = AppLogger.tag(this)
    }

    private val syncScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private fun userIncomeRef() = firestore
        .collection("users")
        .document(auth.currentUser!!.uid)
        .collection("income")

    // ── Writes ─────────────────────────────────────────────────────────────

    override suspend fun addIncome(income: Income) {
        val docRef = userIncomeRef().add(income.toDto()).await()
        incomeDao.upsert(income.copy(id = docRef.id).toEntity())
    }

    override suspend fun updateIncome(income: Income) {
        userIncomeRef().document(income.id).set(income.toDto()).await()
        incomeDao.upsert(income.toEntity())
    }

    override suspend fun deleteIncome(incomeId: String) {
        userIncomeRef().document(incomeId).delete().await()
        incomeDao.deleteById(incomeId)
    }

    // ── Reads (Room as source of truth) ────────────────────────────────────

    override fun getIncomeByMonth(year: Int, month: Int): Flow<List<Income>> {
        syncFirestoreMonthToRoom(year, month)
        return incomeDao.getByMonth(year, month).map { it.toDomainList() }
    }

    override fun getRecentIncome(limit: Int): Flow<List<Income>> =
        incomeDao.getRecent(limit).map { it.toDomainList() }

    override fun observeIncomeById(incomeId: String): Flow<Income> = callbackFlow {
        val listener = userIncomeRef()
            .document(incomeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val income = snapshot
                    ?.toObject(IncomeDto::class.java)
                    ?.toDomain(snapshot.id)
                if (income != null) {
                    syncScope.launch { incomeDao.upsert(income.toEntity()) }
                    trySend(income)
                }
            }
        awaitClose { listener.remove() }
    }

    // ── Private sync ───────────────────────────────────────────────────────

    private fun syncFirestoreMonthToRoom(year: Int, month: Int) {
        syncScope.launch {
            userIncomeRef()
                .whereEqualTo("year", year)
                .whereEqualTo("month", month)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null) {
                        AppLogger.e(TAG, "Firestore income sync error for $year/$month", error)
                        return@addSnapshotListener
                    }
                    val income = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(IncomeDto::class.java)?.toDomain(doc.id)
                    }
                    syncScope.launch { incomeDao.upsertAll(income.toEntityList()) }
                }
        }
    }
}
