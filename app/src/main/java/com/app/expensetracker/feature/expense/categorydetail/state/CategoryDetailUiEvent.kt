package com.app.expensetracker.feature.expense.categorydetail.state

import com.app.expensetracker.feature.expense.domain.model.Expense

sealed class CategoryDetailUiEvent {
    object OnBackClicked : CategoryDetailUiEvent()
    object OnAddExpenseClicked : CategoryDetailUiEvent()
    object OnFilterClicked : CategoryDetailUiEvent()
    object OnViewAllExpensesClicked : CategoryDetailUiEvent()
    data class ExpenseClicked(val expense: Expense) : CategoryDetailUiEvent()

    object OnCategoryClicked: CategoryDetailUiEvent()

    data class OnSaveCategoryBudget(
        val amount: Double
    ) : CategoryDetailUiEvent()

    object OnDismissBottomSheet : CategoryDetailUiEvent()
}
