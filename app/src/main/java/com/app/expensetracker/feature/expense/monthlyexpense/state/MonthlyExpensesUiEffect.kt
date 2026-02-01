package com.app.expensetracker.feature.expense.monthlyexpense.state

sealed class MonthlyExpensesUiEffect {

    object NavigateBack : MonthlyExpensesUiEffect()

    object NavigateToAddExpense : MonthlyExpensesUiEffect()

    data class NavigateToExpenseDetail(
        val expenseId: String
    ) : MonthlyExpensesUiEffect()
}
