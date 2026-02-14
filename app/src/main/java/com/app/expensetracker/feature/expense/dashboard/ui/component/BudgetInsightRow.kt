package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.domain.model.BudgetInsightType
import com.app.expensetracker.feature.expense.domain.model.BudgetInsightUi

@Composable
fun BudgetInsightRow(
    insight: BudgetInsightUi,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        when (insight.type) {
            BudgetInsightType.OVER_BUDGET ->
                MaterialTheme.colorScheme.error.copy(alpha = 0.08f)
            BudgetInsightType.WITHIN_BUDGET ->
                Color(0xFF2E7D32).copy(alpha = 0.08f)
        }

    val iconTint =
        when (insight.type) {
            BudgetInsightType.OVER_BUDGET ->
                MaterialTheme.colorScheme.error
            BudgetInsightType.WITHIN_BUDGET ->
                Color(0xFF2E7D32)
        }

    val icon =
        when (insight.type) {
            BudgetInsightType.OVER_BUDGET ->
                Icons.Outlined.Warning
            BudgetInsightType.WITHIN_BUDGET ->
                Icons.Outlined.CheckCircle
        }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = insight.message,
            style = MaterialTheme.typography.bodyMedium,
            color = iconTint
        )
    }
}
