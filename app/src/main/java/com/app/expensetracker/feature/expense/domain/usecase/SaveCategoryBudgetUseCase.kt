package com.app.expensetracker.feature.expense.domain.usecase

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository

class SaveCategoryBudgetUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        category: ExpenseCategory,
        amount: Double
    ) = repository.saveCategoryBudget(year, month, category, amount)
}
