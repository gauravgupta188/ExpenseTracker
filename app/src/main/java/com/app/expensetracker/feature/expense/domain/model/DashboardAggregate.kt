package com.app.expensetracker.feature.expense.domain.model

import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

 data class DashboardAggregate(
    val expenses: List<Expense>,
    val totalAmount: Double,
    val monthlyBudget: Double?,
    val remainingBudget: Double,
    val topCategories: List<CategorySummaryUiModel>
)
