package com.app.expensetracker.feature.expense.dashboard.ui.model

data class MonthItemUiModel(
    val month: Int, // 1..12
    val label: String,
    val enabled: Boolean
)
