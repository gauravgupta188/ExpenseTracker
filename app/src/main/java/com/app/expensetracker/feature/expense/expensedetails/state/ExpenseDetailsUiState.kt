package com.app.expensetracker.feature.expense.expensedetails.state

import com.app.expensetracker.core.utils.DEFAULT_CURRENCY_CODE
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider

data class ExpenseDetailUiState(
    val isLoading: Boolean = false,
    val expense: Expense? = null,
    val errorMessage: String? = null,
    val isEditMode: Boolean = false,
    val isDeleteConfirmationVisible: Boolean = false,
    val currency: CurrencyItem =
        CurrencyProvider.getCurrencyByCode(DEFAULT_CURRENCY_CODE),

){

}
