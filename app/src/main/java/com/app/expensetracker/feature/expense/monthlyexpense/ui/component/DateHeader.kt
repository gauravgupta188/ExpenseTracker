package com.app.expensetracker.feature.expense.monthlyexpense.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DateHeader(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.outline
    )
}
