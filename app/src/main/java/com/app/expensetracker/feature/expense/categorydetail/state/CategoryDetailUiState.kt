package com.app.expensetracker.feature.expense.categorydetail.state

import com.app.expensetracker.core.utils.DEFAULT_CURRENCY_CODE
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider

data class CategoryDetailUiState(
    val category: ExpenseCategory,
    val yearMonth: YearMonthUiModel,

    val totalSpent: Double = 0.0,
    val budgetAmount: Double? = null,

    val expenses: List<Expense> = emptyList(),
    val showCategoryBudgetSheet: Boolean = false,
    val currency: CurrencyItem =
        CurrencyProvider.getCurrencyByCode(DEFAULT_CURRENCY_CODE),

    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val remainingAmount: Double?
        get() = budgetAmount?.minus(totalSpent)

    val progress: Float
        get() = if (budgetAmount != null && budgetAmount > 0) {
            (totalSpent / budgetAmount).coerceIn(0.0, 1.0).toFloat()
        } else 0f
}
