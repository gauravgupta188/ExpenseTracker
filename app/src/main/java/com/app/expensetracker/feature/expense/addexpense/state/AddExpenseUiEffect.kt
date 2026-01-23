package com.app.expensetracker.feature.expense.addexpense.state

import java.time.LocalDate

sealed interface AddExpenseUiEffect {
    object ShowDatePicker : AddExpenseUiEffect
    object ShowTimePicker : AddExpenseUiEffect
    object NavigateBack : AddExpenseUiEffect
    data class ShowSnackBar(val message: String) : AddExpenseUiEffect
}