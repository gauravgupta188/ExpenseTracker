package com.app.expensetracker.feature.expense.expensedetails.state

sealed class ExpenseDetailUiEffect {

    object NavigateBack : ExpenseDetailUiEffect()

    data class NavigateToEdit(
        val expenseId: String
    ) : ExpenseDetailUiEffect()

    data class ShowError(
        val message: String
    ) : ExpenseDetailUiEffect()
}
