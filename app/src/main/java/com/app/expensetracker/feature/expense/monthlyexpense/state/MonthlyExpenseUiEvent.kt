package com.app.expensetracker.feature.expense.monthlyexpense.state

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

sealed class MonthlyExpensesUiEvent {

    object OnBackClicked : MonthlyExpensesUiEvent()

    object OnAddExpenseClicked : MonthlyExpensesUiEvent()

    data class OnExpenseClicked(
        val expenseId: String
    ) : MonthlyExpensesUiEvent()

    data class OnMonthChanged(
        val month: YearMonthUiModel
    ) : MonthlyExpensesUiEvent()
}
