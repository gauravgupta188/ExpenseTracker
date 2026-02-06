package com.app.expensetracker.feature.expense.dashboard.state

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

/**
 * Immutable UI state for the Expense List screen.
 */
data class ExpenseUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val months: List<YearMonthUiModel> = emptyList(),
    val selectedMonth: YearMonthUiModel,

    val expenses: List<Expense> = emptyList(),
    val recentExpenses: List<Expense> = emptyList(),
    val totalAmount: Double = 0.0,
    val monthlyBudget: Double = 0.0,
    val remainingBudget: Double = 0.0,

    val topCategories: List<CategorySummaryUiModel> = emptyList(),

    val showMonthPicker: Boolean = false,
)
  //Effects
