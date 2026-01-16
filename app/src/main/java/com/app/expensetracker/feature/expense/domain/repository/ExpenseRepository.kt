package com.app.expensetracker.feature.expense.domain.repository

import com.app.expensetracker.feature.expense.domain.model.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Expense repository.
 * Defined in the domain layer to follow the Dependency Inversion Principle.
 */

interface ExpenseRepository {

    suspend fun addExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expenseId: String)

    fun getExpensesByMonth(
        year: Int,
        month: Int
    ): Flow<List<Expense>>

    fun getRecentExpenses(
        limit: Int = 10
    ): Flow<List<Expense>>
}

