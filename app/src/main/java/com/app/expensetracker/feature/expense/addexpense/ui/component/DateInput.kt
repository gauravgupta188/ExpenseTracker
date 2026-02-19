package com.app.expensetracker.feature.expense.addexpense.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            border = null, // 🔥 removes border
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = formatDateTime(date),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun previewDateInput() {
    DateInput(LocalDateTime.now(), onClick = {})
}

