package com.app.expensetracker.feature.expense.data.repository

import android.util.Log
import com.app.expensetracker.feature.expense.data.mapper.toDomain
import com.app.expensetracker.feature.expense.data.mapper.toDto
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.collections.orEmpty

class ExpenseRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExpenseRepository {


    private fun budgetDoc(year: Int, month: Int) =
        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("budgets")
            .document("$year-${month.toString().padStart(2, '0')}")

    private fun userExpensesRef() =
        firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("expenses")

    override suspend fun addExpense(expense: Expense) {
        userExpensesRef()
            .add(expense.toDto())
            .await()
    }

    override suspend fun updateExpense(expense: Expense) {
        userExpensesRef()
            .document(expense.id)
            .set(expense.toDto())
            .await()
    }

    override fun observeExpenseById(expenseId: String): Flow<Expense> = callbackFlow {
        val listenerRegistration =
            userExpensesRef()
                .document(expenseId)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot == null || !snapshot.exists()) {
                        close(IllegalStateException("Expense not found"))
                        return@addSnapshotListener
                    }

                    val expense = snapshot
                        .toObject(ExpenseDto::class.java)
                        ?.toDomain(snapshot.id)

                    if (expense != null) {
                        trySend(expense)
                    }
                }

        awaitClose {
            listenerRegistration.remove()
        }
    }



    override suspend fun deleteExpense(expenseId: String) {
        userExpensesRef()
            .document(expenseId)
            .delete()
            .await()
    }

    override fun getExpensesByMonth(
        year: Int,
        month: Int
    ): Flow<List<Expense>> = callbackFlow {

        Log.d("Month",month.toString())
        val listener = userExpensesRef()
            .whereEqualTo("year", year)
            .whereEqualTo("month", month)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val expenses = snapshot?.documents?.map {
                    it.toObject(ExpenseDto::class.java)!!
                        .toDomain(it.id)
                }.orEmpty()

               // Log.d("Expenses",expenses.toString())

                trySend(expenses)
            }

        awaitClose { listener.remove() }
    }

    override fun getRecentExpenses(
        limit: Int
    ): Flow<List<Expense>> = callbackFlow {

        val listener = userExpensesRef()
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val expenses = snapshot?.documents?.map {
                    it.toObject(ExpenseDto::class.java)!!
                        .toDomain(it.id)
                }.orEmpty()

                trySend(expenses)
            }

        awaitClose { listener.remove() }
    }

    override fun observeMonthlyBudget(
        year: Int,
        month: Int
    ): Flow<MonthlyBudget?> = callbackFlow {

        val listener = budgetDoc(year, month)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot == null || !snapshot.exists()) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val dto = snapshot.toObject(MonthlyBudgetDto::class.java)!!

                val domain = MonthlyBudget(
                    year = year,
                    month = month,
                    monthlyBudget = dto.monthlyBudget,
                    categoryBudgets = dto.categoryBudgets.mapKeys {
                        ExpenseCategory.fromValue(it.key)
                    }
                )

                trySend(domain)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun saveMonthlyBudget(
        year: Int,
        month: Int,
        amount: Double
    ) {
        budgetDoc(year, month)
            .set(
                mapOf(
                    "monthlyBudget" to amount,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                SetOptions.merge()
            )
            .await()
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

            val existingBudgets =
                snapshot.get("categoryBudgets") as? Map<String, Double>
                    ?: emptyMap()

            val updatedBudgets = existingBudgets.toMutableMap().apply {
                put(category.value, amount)
            }

            transaction.set(
                docRef,
                mapOf(
                    "categoryBudgets" to updatedBudgets,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                SetOptions.merge()
            )
        }.await()
    }
}