package com.app.expensetracker.feature.expense.categorydetail.state

import com.app.expensetracker.feature.expense.domain.model.Expense

sealed class CategoryDetailUiEffect {
    object NavigateBack : CategoryDetailUiEffect()
    object NavigateToAddExpense : CategoryDetailUiEffect()
    object OpenFilter : CategoryDetailUiEffect()
    data class NavigateToExpenseDetail(val expense : Expense) : CategoryDetailUiEffect()
    data class ShowError(val message: String) : CategoryDetailUiEffect()
}
