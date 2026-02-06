package com.app.expensetracker.feature.expense.dashboard.state

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

/**
 * Explicit UI events for the Expense List screen.
 */
sealed class ExpenseUiEvent {
    data class DeleteExpense(val expense: Expense) : ExpenseUiEvent()
    object AddExpenseClicked : ExpenseUiEvent()
    data class ExpenseClicked(val expense: Expense) : ExpenseUiEvent()
    data class OnMonthSelected(val month: YearMonthUiModel) : ExpenseUiEvent()

    data class OnCategoryClicked(
        val category: ExpenseCategory
    ) : ExpenseUiEvent()

    object OnViewAllCategoriesClicked : ExpenseUiEvent()

    object DismissMonthPicker : ExpenseUiEvent()
    // other events
}
