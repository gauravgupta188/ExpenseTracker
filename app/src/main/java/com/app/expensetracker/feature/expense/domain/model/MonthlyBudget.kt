package com.app.expensetracker.feature.expense.domain.model

data class MonthlyBudget(
    val year: Int,
    val month: Int,
    val monthlyBudget: Double?,
    val categoryBudgets: Map<ExpenseCategory, Double>
)
