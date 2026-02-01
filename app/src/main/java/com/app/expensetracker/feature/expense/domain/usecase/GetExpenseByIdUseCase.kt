package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository

class GetExpenseByIdUseCase(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expenseId: String): Expense {
        return repository.getExpenseById(expenseId)
    }
}
