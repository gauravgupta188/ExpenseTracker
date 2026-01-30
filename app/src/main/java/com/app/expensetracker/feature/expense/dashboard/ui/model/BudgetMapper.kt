package com.app.expensetracker.feature.expense.dashboard.ui.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BudgetUsageLevel.color(): Color {
    return when (this) {
        BudgetUsageLevel.NONE -> MaterialTheme.colorScheme.secondary
        BudgetUsageLevel.SAFE -> Color(0xFF4CAF50)      // Green
        BudgetUsageLevel.WARNING -> Color(0xFFFF9800)   // Orange
        BudgetUsageLevel.CRITICAL -> Color(0xFFF44336)  // Red
    }
}
