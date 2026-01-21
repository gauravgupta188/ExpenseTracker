package com.app.expensetracker.feature.expense.addexpense.state

sealed interface AddExpenseUiEffect {
    object ShowDatePicker : AddExpenseUiEffect
    object NavigateBack : AddExpenseUiEffect
    data class ShowSnackBar(val message: String) : AddExpenseUiEffect
}