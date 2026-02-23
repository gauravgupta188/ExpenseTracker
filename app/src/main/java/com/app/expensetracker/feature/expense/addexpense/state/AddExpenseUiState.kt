package com.app.expensetracker.feature.expense.addexpense.state

import com.app.expensetracker.core.utils.DEFAULT_CURRENCY_CODE
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider
import java.time.LocalDate
import java.time.LocalDateTime

data class AddExpenseUiState(
    val mode: ExpenseFormMode = ExpenseFormMode.Add,
    val amount: Double = 0.0,
    val selectedCategory: ExpenseCategory? = null,
    val selectedDate: LocalDateTime = LocalDateTime.now(),
   // val selectedTime: LocalDateTime = LocalDateTime.now(),
    val note: String = "",
    val isCategorySheetVisible: Boolean = false,
    val currency: CurrencyItem =
        CurrencyProvider.getCurrencyByCode(DEFAULT_CURRENCY_CODE),

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)