package com.app.expensetracker.feature.expense.summary.model

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

data class CategorySummaryUiModel(
    val category: ExpenseCategory,
    val spentAmount: Double,
    val budgetAmount: Double? = null,
    val description: String = ""
) {

    val hasBudget: Boolean
        get() = budgetAmount != null && budgetAmount > 0.0

    val progress: Float
        get() = if (hasBudget) {
            (spentAmount / budgetAmount!!)
                .coerceIn(0.0, 1.0)
                .toFloat()
        } else {
            0f
        }

    val usagePercent: Int
        get() = if (hasBudget) {
            ((spentAmount / budgetAmount!!) * 100).toInt()
        } else {
            0
        }

    val isOverBudget: Boolean
        get() = hasBudget && spentAmount > budgetAmount!!
}
