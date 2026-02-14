package com.app.expensetracker.feature.expense.domain.model

data class BudgetInsightUi(
    val type: BudgetInsightType,
    val message: String
)

enum class BudgetInsightType {
    OVER_BUDGET,
    WITHIN_BUDGET
}
