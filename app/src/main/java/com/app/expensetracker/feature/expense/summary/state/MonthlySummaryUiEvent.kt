package com.app.expensetracker.feature.expense.summary.state

sealed interface MonthlySummaryUiEvent {
    object OnBackClicked : MonthlySummaryUiEvent
    object OnMonthSelectorClicked : MonthlySummaryUiEvent
    object OnAddExpenseClicked : MonthlySummaryUiEvent
}
