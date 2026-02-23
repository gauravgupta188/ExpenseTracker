package com.app.expensetracker.feature.expense.summary.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard
import com.app.expensetracker.core.components.AppCircularIcon
import com.app.expensetracker.feature.expense.dashboard.ui.component.EditBudgetIcon
import com.app.expensetracker.feature.expense.dashboard.ui.model.color
import com.app.expensetracker.feature.expense.domain.utils.monthlyUsageLevel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.ui.mapper.icon
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem

@Composable
fun CategoryBreakdownItems(
    model: CategorySummaryUiModel,
    currencyItem: CurrencyItem,
    onCategoryClick: (String) -> Unit,
    onEditBudgetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedCategoryBudget = model.budgetAmount ?: "No budget set"

    AppCard (
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCategoryClick(model.category.value) },
        contentPadding = PaddingValues(4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            /* ───── Top Row ───── */
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                AppCircularIcon(icon = model.category.icon)

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = model.category.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = model.category.value,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${currencyItem.symbol}${model.spentAmount}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFFF9800),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if(model.budgetAmount != null) "TARGET: ${currencyItem.symbol}${model.budgetAmount}" else "No budget set",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

               /* IconButton(
                    modifier = Modifier.background(Color.Red),
                    onClick = {
                        onEditBudgetClick(model.category.name)
                    }
                )*/

                EditBudgetIcon(onClick = {
                    onEditBudgetClick(model.category.name)
                })
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ───── Progress Bar ───── */
            LinearProgressIndicator(
                progress = { model.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = model.usageLevel.color(),
                trackColor = Color(0xFFEDEDED)
            )

            Spacer(modifier = Modifier.height(6.dp))

            /* ───── Status Row ───── */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${(model.progress * 100).toInt()}% used",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (model.isOverBudget) Color.Red else Color.Gray
                )

                Text(
                    text = monthlyUsageLevel(budget = model.budgetAmount, spent = model.spentAmount).value,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (model.isOverBudget) Color.Red else MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
