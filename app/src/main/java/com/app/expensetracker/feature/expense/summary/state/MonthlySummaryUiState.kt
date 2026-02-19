package com.app.expensetracker.feature.expense.summary.state

import com.app.expensetracker.core.utils.DEFAULT_CURRENCY_CODE
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider

data class MonthlySummaryUiState(
    val selectedMonth: YearMonthUiModel,

    val totalSpent: Double = 0.0,
    val monthlyBudget: Double = 0.0,
    val remainingAmount: Double = 0.0,

    val spendingChangePercent: Int = 0,
    val isSpendingDown: Boolean = true,

    val insightMessage: String = "",

    val categories: List<CategorySummaryUiModel> = emptyList(),
   // val categoryBudgets: Map<ExpenseCategory, Double> = emptyMap(),

    val showMonthlyBudgetSheet: Boolean = false,
    val currency: CurrencyItem =
        CurrencyProvider.getCurrencyByCode(DEFAULT_CURRENCY_CODE),

    val editingCategory: CategorySummaryUiModel? = null,
    val showCategoryBudgetSheet: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

