package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to retrieve the list of expenses.
 */

class GetExpensesByMonthUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(
        year: Int,
        month: Int
    ): Flow<List<Expense>> =
        repository.getExpensesByMonth(year, month)
}
