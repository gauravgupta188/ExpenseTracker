package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseByIdUseCase(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expenseId: String): Flow<Expense> {
        return repository.observeExpenseById(expenseId)
    }
}
