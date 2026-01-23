package com.app.expensetracker.feature.expense.categorydetail.state

sealed class CategoryDetailUiEvent {
    object OnBackClicked : CategoryDetailUiEvent()
    object OnAddExpenseClicked : CategoryDetailUiEvent()
    object OnFilterClicked : CategoryDetailUiEvent()
}
