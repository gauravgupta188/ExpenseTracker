package com.app.expensetracker.feature.expense.categorydetail.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard
import com.app.expensetracker.core.utils.formattedAmount
import com.app.expensetracker.core.utils.formattedCurrencyWithAmount
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem

@Composable
fun CategoryBudgetCard(
    totalSpent: Double,
    budget: Double?,
    progress: Float,
    currencyItem: CurrencyItem,
    budgetUsageColor : Color,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("TOTAL SPENT", style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = formattedCurrencyWithAmount(
                            amount = totalSpent,
                            currency = currencyItem
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("TARGET BUDGET", style = MaterialTheme.typography.labelSmall)
                    if (budget != null) {
                        Text(
                            text =  if(budget > 0) formattedCurrencyWithAmount(
                                amount = budget,
                                currency = currencyItem
                            ) else "Tap to set budget" ,
                            style = if(budget > 0) MaterialTheme.typography.titleMedium else MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = progress,
                color = budgetUsageColor,
                trackColor = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(Modifier.height(8.dp))

            if (budget != null) {
                Text(
                    text = "${(progress * 100).toInt()}% of budget consumed",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
