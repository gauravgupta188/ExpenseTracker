package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to retrieve the list of expenses.
 */

class GetRecentExpenseByMonthUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(
    ): Flow<List<Expense>> =
        repository.getRecentExpenses()
}
