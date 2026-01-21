package com.app.expensetracker.feature.expense.addexpense.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.utils.formatDateTime
import java.time.LocalDateTime

@Composable
fun DateInput(
    date: LocalDateTime,
    onClick: () -> Unit
) {
    Column {
        Text(
            text = "Date",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = formatDateTime(date)
            )
        }
    }
}
