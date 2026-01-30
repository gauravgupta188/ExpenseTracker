package com.app.expensetracker.feature.expense.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ExpenseSectionHeader(
    modifier: Modifier = Modifier.Companion,
    onViewAllClick: () -> Unit,
    title: String,
    subTitle:String = "View All"
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.Companion.weight(1f)
        )

        TextButton(onClick = onViewAllClick) {
            Text(
                text = subTitle,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Companion.SemiBold)
            )
        }
    }
}