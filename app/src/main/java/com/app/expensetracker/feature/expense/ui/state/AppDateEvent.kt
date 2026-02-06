package com.app.expensetracker.feature.expense.ui.state

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

sealed interface AppDateEvent {
    object OpenPicker : AppDateEvent
    object ClosePicker : AppDateEvent

    data class MonthSelected(val month: YearMonthUiModel) : AppDateEvent
    object PreviousYear : AppDateEvent
    object NextYear : AppDateEvent

    object ApplySelection : AppDateEvent
}
