package com.app.expensetracker.feature.expense.ui.state

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

data class AppDateUiState(
    val selectedMonth: YearMonthUiModel = YearMonthUiModel.current(),
   // val tempMonth: YearMonthUiModel = YearMonthUiModel.current(),
    val isPickerVisible: Boolean = false
)
