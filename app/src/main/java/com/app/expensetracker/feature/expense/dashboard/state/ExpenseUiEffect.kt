package com.app.expensetracker.feature.expense.dashboard.state

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

sealed class ExpenseUiEffect {
    data class NavigateToCategory(
        val category: ExpenseCategory
    ) : ExpenseUiEffect()

    object NavigateToAllCategories : ExpenseUiEffect()
}
