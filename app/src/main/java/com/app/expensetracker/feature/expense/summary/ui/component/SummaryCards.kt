package com.app.expensetracker.feature.expense.summary.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SummaryCards(
    totalSpent: Double,
    remaining: Double,
    budget: Double,
    percent: Int,
    isDown: Boolean
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        SummaryCard(
            title = "TOTAL SPENT",
            amount = totalSpent,
            footer = "${percent}% vs last mo.",
            footerColor = if (isDown) Color.Green else Color.Red
        )

        SummaryCard(
            title = "REMAINING",
            amount = remaining,
            footer = "Budget: ₹$budget",
            footerColor = Color.Gray
        )
    }
}
