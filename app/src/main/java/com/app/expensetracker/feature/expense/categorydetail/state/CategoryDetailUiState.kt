package com.app.expensetracker.feature.expense.categorydetail.state

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

data class CategoryDetailUiState(
    val category: ExpenseCategory,
    val yearMonth: YearMonthUiModel,

    val totalSpent: Double = 0.0,
    val budgetAmount: Double? = null,

    val expenses: List<Expense> = emptyList(),

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
