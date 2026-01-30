package com.app.expensetracker.feature.expense.summary.state

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

data class MonthlySummaryUiState(
    val selectedMonth: YearMonthUiModel,

    val totalSpent: Double = 0.0,
    val monthlyBudget: Double = 0.0,
    val remainingAmount: Double = 0.0,

    val spendingChangePercent: Int = 0,
    val isSpendingDown: Boolean = true,

    val insightMessage: String = "You saved $200 more on groceries this month",

    val categories: List<CategorySummaryUiModel> = emptyList(),
   // val categoryBudgets: Map<ExpenseCategory, Double> = emptyMap(),

    val showMonthlyBudgetSheet: Boolean = false,

    val editingCategory: CategorySummaryUiModel? = null,
    val showCategoryBudgetSheet: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

