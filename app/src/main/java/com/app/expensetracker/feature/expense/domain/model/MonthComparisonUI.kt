package com.app.expensetracker.feature.expense.domain.model

data class MonthComparisonUi(
    val percentageChange: Int,   // +18 or -12
    val isIncrease: Boolean,
    val summaryText: String      // precomputed text
)
