package com.app.expensetracker.feature.expense.domain.utils

import com.app.expensetracker.feature.expense.dashboard.ui.model.BudgetUsageLevel

fun monthlyUsageLevel(
    spent: Double,
    budget: Double?
): BudgetUsageLevel {
    if (budget == null || budget == 0.0) return BudgetUsageLevel.NONE
    val percent = (spent / budget) * 100
    return when {
        percent < 70 -> BudgetUsageLevel.SAFE
        percent < 90 -> BudgetUsageLevel.WARNING
        else -> BudgetUsageLevel.CRITICAL
    }
}