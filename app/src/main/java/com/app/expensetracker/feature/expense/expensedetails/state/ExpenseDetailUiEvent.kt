package com.app.expensetracker.feature.expense.expensedetails.state

sealed class ExpenseDetailUiEvent {

    object OnBackClicked : ExpenseDetailUiEvent()

    object OnEditClicked : ExpenseDetailUiEvent()

    object OnDeleteClicked : ExpenseDetailUiEvent()

    object OnConfirmDelete : ExpenseDetailUiEvent()

    object  OnDismissDeleteDialog : ExpenseDetailUiEvent()
}
