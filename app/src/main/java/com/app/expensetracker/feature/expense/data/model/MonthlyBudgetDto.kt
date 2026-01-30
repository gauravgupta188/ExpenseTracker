package com.app.expensetracker.feature.expense.data.model

import com.google.firebase.Timestamp


data class MonthlyBudgetDto(
    val monthlyBudget: Double? = null,
    val categoryBudgets: Map<String, Double> = emptyMap(),
    val updatedAt: Timestamp? = null
)
