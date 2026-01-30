package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository

class ObserveMonthlyBudgetUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(year: Int, month: Int) =
        repository.observeMonthlyBudget(year, month)
}
