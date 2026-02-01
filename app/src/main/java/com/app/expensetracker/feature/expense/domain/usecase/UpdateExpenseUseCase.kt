package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository

class UpdateExpenseUseCase(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {
        repository.updateExpense(expense)
    }
}
