package com.app.expensetracker.feature.expense.addexpense.state

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

sealed interface AddExpenseUiEvent {

    data class AmountChanged(val value: String) : AddExpenseUiEvent
    data class CategorySelected(val category: ExpenseCategory) : AddExpenseUiEvent
    data class NoteChanged(val value: String) : AddExpenseUiEvent

    object SaveClicked : AddExpenseUiEvent
}