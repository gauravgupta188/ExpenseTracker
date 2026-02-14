package com.app.expensetracker.feature.expense.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCircularIcon
import com.app.expensetracker.core.utils.formatDateTime
import com.app.expensetracker.core.utils.formattedCurrencyWithAmount
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.ui.mapper.icon
import kotlin.math.max


@Composable
fun ExpenseItem(
    expense: Expense,
    onClick: () -> Unit ={}

) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {


        AppCircularIcon(expense.category.icon)

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = expense.note?.trim() ?: "",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = formatDateTime(expense.date),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


        Text(
            text = formattedCurrencyWithAmount(amount = expense.amount),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
        )
    }


}


