package com.app.expensetracker.feature.expense.domain.model

import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

data class MonthlySummary(
    val totalSpent: Double,
    val categories: List<CategorySummaryUiModel>
)