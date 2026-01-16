package com.app.expensetracker.feature.expense.data.repository

import com.app.expensetracker.feature.expense.data.mapper.toDomain
import com.app.expensetracker.feature.expense.data.mapper.toDto
import com.app.expensetracker.feature.expense.data.model.ExpenseDto
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ExpenseRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExpenseRepository {

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
}