package com.app.expensetracker.feature.expense.dashboard.state

import com.app.expensetracker.feature.auth.domain.model.AuthUser
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.MonthHighlightsUi
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider
import com.google.rpc.context.AttributeContext
import java.time.Month

/**
 * Immutable UI state for the Expense List screen.
 */
data class ExpenseUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val months: List<YearMonthUiModel> = emptyList(),
    val selectedMonth: YearMonthUiModel,
    val isCurrentMonth: Boolean = true,
    val currentUser : AuthUser? = null,
    val displayName : String = "",

    val expenses: List<Expense> = emptyList(),
    val recentExpenses: List<Expense> = emptyList(),
    // Past month only
    val monthHighlights: MonthHighlightsUi? = null,
    val totalAmount: Double = 0.0,
    val monthlyBudget: Double = 0.0,
    val remainingBudget: Double = 0.0,

    val topCategories: List<CategorySummaryUiModel> = emptyList(),
    val allCategories: List<CategorySummaryUiModel>? = emptyList(),

    val showMonthPicker: Boolean = false,
    val currency: CurrencyItem =
        CurrencyProvider.getCurrencyByCode("USD"),
)
//Effects
