package com.app.expensetracker.feature.expense.summary.ui.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard
import com.app.expensetracker.feature.expense.dashboard.ui.component.EditBudgetIcon

@Composable
fun SummaryStatCard(
    title: String,
    amount: Double,
    formattedAmount: String,
    footerText: String,
    footerColor: Color,
    isEdit: Boolean = false,
    budgetColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    budgetClick: () -> Unit = {}
) {
    AppCard(
        modifier = modifier.clickable(onClick = budgetClick),
    ) {
        Column {

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = formattedAmount,
                    style = MaterialTheme.typography.headlineSmall,
                    color = budgetColor
                )
                Spacer(Modifier.weight(1f))

                if (isEdit)
                    EditBudgetIcon(onClick = {}, iconSize = 16.dp)

            }
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = footerText,
                    style = MaterialTheme.typography.bodySmall,
                    color = footerColor
                )
            }
        }
    }

