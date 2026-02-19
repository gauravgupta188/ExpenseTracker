package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.ui.model.color
import com.app.expensetracker.feature.expense.domain.utils.monthlyUsageLevel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.ui.mapper.color
import com.app.expensetracker.feature.expense.ui.mapper.displayName
import com.app.expensetracker.feature.expense.ui.mapper.icon
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem

@Composable
fun CategoryItemRow(
    model: CategorySummaryUiModel,
    currencyItem: CurrencyItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = model.category.icon,
                contentDescription = null,
                tint = model.usageLevel.color()
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = model.category.displayName,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "${currencyItem.symbol}${model.spentAmount.toInt()}",
                style = MaterialTheme.typography.bodyLarge,
                color = model.usageLevel.color()
            )
        }

        Spacer(Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = model.progress,
            modifier = Modifier.fillMaxWidth(),
            color = model.usageLevel.color(),
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
