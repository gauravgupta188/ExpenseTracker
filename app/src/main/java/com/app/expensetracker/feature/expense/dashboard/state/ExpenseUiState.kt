package com.app.expensetracker.feature.expense.dashboard.state

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

/**
 * Immutable UI state for the Expense List screen.
 */
data class ExpenseUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val months: List<YearMonthUiModel> = emptyList(),
    val selectedMonth: YearMonthUiModel,

    val expenses: List<Expense> = emptyList(),
    val totalAmount: Double = 0.0
)

