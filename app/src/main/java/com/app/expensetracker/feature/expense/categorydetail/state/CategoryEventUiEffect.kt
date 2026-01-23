package com.app.expensetracker.feature.expense.categorydetail.state

sealed class CategoryDetailUiEffect {
    object NavigateBack : CategoryDetailUiEffect()
    object NavigateToAddExpense : CategoryDetailUiEffect()
    object OpenFilter : CategoryDetailUiEffect()
}
