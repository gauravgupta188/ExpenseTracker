package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository

class SaveMonthlyBudgetUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        amount: Double
    ) = repository.saveMonthlyBudget(year, month, amount)
}
