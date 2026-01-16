package com.app.expensetracker.feature.expense.dashboard.state

import com.app.expensetracker.feature.expense.domain.model.Expense

/**
 * Explicit UI events for the Expense List screen.
 */
sealed class ExpenseUiEvent {
    data class DeleteExpense(val expense: Expense) : ExpenseUiEvent()
    object AddExpenseClicked : ExpenseUiEvent()
    data class ExpenseClicked(val expense: Expense) : ExpenseUiEvent()
}
