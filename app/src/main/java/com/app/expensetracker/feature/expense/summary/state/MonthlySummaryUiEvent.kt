package com.app.expensetracker.feature.expense.summary.state

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

sealed class MonthlySummaryUiEvent {
    object OnBackClicked : MonthlySummaryUiEvent()
    object OnAddExpenseClicked : MonthlySummaryUiEvent()
    object OnMonthSelectorClicked : MonthlySummaryUiEvent()
    data class OnMonthSelected(val month: YearMonthUiModel) : MonthlySummaryUiEvent()
}

