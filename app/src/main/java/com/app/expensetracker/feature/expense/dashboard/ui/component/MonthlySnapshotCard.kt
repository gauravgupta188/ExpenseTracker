package com.app.expensetracker.feature.expense.dashboard.ui.component
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard
import com.app.expensetracker.core.utils.formattedAmount
import com.app.expensetracker.core.utils.formattedCurrencyWithAmount
import com.app.expensetracker.core.utils.getDefaultCurrency
import com.app.expensetracker.feature.expense.dashboard.ui.model.color
import com.app.expensetracker.feature.expense.domain.utils.monthlyUsageLevel

@Composable
fun MonthlySnapshotCard(
    spend: Double,
    remaining: Double,
    monthlyBudget:Double,
    modifier: Modifier = Modifier,
    onBudgetEditClick:() -> Unit
) {

    val budgetColor = monthlyUsageLevel(spend,monthlyBudget).color()
    AppCard(modifier = Modifier.clickable(onClick = onBudgetEditClick)) {
        Column{

            Text(
                text = "Total Spent".uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = formattedCurrencyWithAmount(amount = spend),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(thickness = 0.4.dp, color = Color.Gray.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 16.dp))




            Row(  verticalAlignment = Alignment.CenterVertically,  modifier = modifier
                .fillMaxWidth()
                ) {
                Column {
                    Text(
                        text = "Budget Remaining".uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if(monthlyBudget > 0){
                        Text(
                            text =formattedCurrencyWithAmount(amount = remaining),
                            style = MaterialTheme.typography.headlineSmall,
                            color = budgetColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else {
                        Text(
                            text = "Tap to set budget",
                            style = MaterialTheme.typography.labelSmall,
                            color = budgetColor
                        )
                    }


                }
            }
        }
    }
}
