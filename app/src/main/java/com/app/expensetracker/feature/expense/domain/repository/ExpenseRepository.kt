package com.app.expensetracker.feature.expense.domain.repository

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.MonthlyBudget
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Expense repository.
 * Defined in the domain layer to follow the Dependency Inversion Principle.
 */

interface ExpenseRepository {

    suspend fun addExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    suspend fun getExpenseById(expenseId: String): Expense

    suspend fun deleteExpense(expenseId: String)


    fun getExpensesByMonth(
        year: Int,
        month: Int
    ): Flow<List<Expense>>

    fun getRecentExpenses(
        limit: Int = 5
    ): Flow<List<Expense>>

    fun observeMonthlyBudget(
        year: Int,
        month: Int
    ): Flow<MonthlyBudget?>

    suspend fun saveMonthlyBudget(
        year: Int,
        month: Int,
        amount: Double
    )

    suspend fun saveCategoryBudget(
        year: Int,
        month: Int,
        category: ExpenseCategory,
        amount: Double
    )
}

