package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel

@Composable
fun MonthSelector(
    months: List<YearMonthUiModel>,
    selectedMonth: YearMonthUiModel,
    onMonthSelected: (YearMonthUiModel) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = months.size,
            key = { index ->
                "${months[index].year}-${months[index].month}"
            }
        ) { index ->

            val month = months[index]
            val isSelected = month == selectedMonth

            MonthItem(
                month = month,
                isSelected = isSelected,
                onClick = { onMonthSelected(month) }
            )
        }
    }
}

@Composable
private fun MonthItem(
    month: YearMonthUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    Color(0xFF1E1E1E)
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = month.label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected)
                Color.White
            else
                Color.Gray
        )

        Spacer(modifier = Modifier.height(2.dp))

       /* Text(
            text = month.year.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected)
                Color.White.copy(alpha = 0.9f)
            else
                Color.Gray
        )*/
    }
}


