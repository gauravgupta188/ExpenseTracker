package com.app.expensetracker.feature.expense.expensedetails.state

import com.app.expensetracker.feature.expense.domain.model.Expense

data class ExpenseDetailUiState(
    val isLoading: Boolean = false,
    val expense: Expense? = null,
    val errorMessage: String? = null,
    val isEditMode: Boolean = false,
    val isDeleteConfirmationVisible: Boolean = false,

){

}
