package com.app.expensetracker.feature.expense.dashboard.ui.model

enum class BudgetUsageLevel(val value:String) {
    NONE("--"),
    SAFE("On Track"),
    WARNING("Approaching Limit"),
    CRITICAL("Exceeding Limit")
}
