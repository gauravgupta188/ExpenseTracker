package com.app.expensetracker.feature.expense.domain.model

import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

data class MonthHighlightsUi(
    val topCategories: List<CategorySummaryUiModel>,
    val budgetInsights: List<BudgetInsightUi>,
    val comparison: MonthComparisonUi
)
