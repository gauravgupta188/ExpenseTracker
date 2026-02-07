package com.app.expensetracker.feature.expense.categorydetail.state

import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEffect

sealed class CategoryDetailUiEffect {
    object NavigateBack : CategoryDetailUiEffect()
    object NavigateToAddExpense : CategoryDetailUiEffect()
    object OpenFilter : CategoryDetailUiEffect()
    data class NavigateToExpenseDetail(val expense : Expense) : CategoryDetailUiEffect()
    data class ShowError(val message: String) : CategoryDetailUiEffect()
}
