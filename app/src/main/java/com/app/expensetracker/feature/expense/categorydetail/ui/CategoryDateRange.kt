package com.app.expensetracker.feature.expense.categorydetail.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

@Composable
fun CategoryDateRange(
    yearMonth: YearMonthUiModel,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard{
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = yearMonth.rangeLabel,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

          /*  IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }*/
        }
    }
}
