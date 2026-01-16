package com.app.expensetracker.feature.expense.summary.state

sealed interface MonthlySummaryUiEffect {
    object NavigateBack : MonthlySummaryUiEffect
    object NavigateToAddExpense : MonthlySummaryUiEffect
    object OpenMonthPicker : MonthlySummaryUiEffect
}