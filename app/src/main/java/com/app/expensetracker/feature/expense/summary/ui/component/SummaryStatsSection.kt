package com.app.expensetracker.feature.expense.summary.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.ui.model.color
import com.app.expensetracker.feature.expense.domain.utils.monthlyUsageLevel
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem

@Composable
fun SummaryStatsSection(
    totalSpent: Double,
    remainingAmount: Double,
    budgetAmount: Double,
    spendingChangePercent: Int,
    isSpendingDown: Boolean,
    currencyItem: CurrencyItem,
    modifier: Modifier = Modifier,
    onBudgetEditClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),

        ) {

            SummaryStatCard(
                title = "TOTAL SPENT",
                amount = totalSpent,
                footerText = if (spendingChangePercent > 0) "$spendingChangePercent% vs last month" else "",
                footerColor = if (isSpendingDown) Color(0xFF2E7D32) else Color.Red,
                modifier = Modifier.weight(1f),
                formattedAmount = "${currencyItem.symbol}${"%,.0f".format(totalSpent)}"

            )

            SummaryStatCard(
                title = "REMAINING",
                amount = remainingAmount,
                footerText = "Budget ${currencyItem.symbol}${budgetAmount.toInt()}",
                footerColor = Color.Gray,
                modifier = Modifier.weight(1f),
                isEdit = true,
                budgetClick = {onBudgetEditClick()},
                budgetColor = monthlyUsageLevel(totalSpent,budgetAmount).color(),
                formattedAmount = if(budgetAmount > 0) "${currencyItem.symbol}${"%,.0f".format(remainingAmount)}" else "No budget set"
            )
        }
    }
}
