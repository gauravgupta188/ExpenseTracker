package com.app.expensetracker.feature.expense.summary.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SummaryStatsSection(
    totalSpent: Double,
    remainingAmount: Double,
    budgetAmount: Double,
    spendingChangePercent: Int,
    isSpendingDown: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SummaryStatCard(
                title = "TOTAL SPENT",
                amount = totalSpent,
                footerText = "$spendingChangePercent% vs last month",
                footerColor = if (isSpendingDown) Color(0xFF2E7D32) else Color.Red,
                modifier = Modifier.weight(1f)
            )

            SummaryStatCard(
                title = "REMAINING",
                amount = remainingAmount,
                footerText = "Budget ₹${budgetAmount.toInt()}",
                footerColor = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
