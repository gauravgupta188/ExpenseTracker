package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository

class DeleteExpenseUseCase(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expenseId: String) {
        repository.deleteExpense(expenseId)
    }
}
