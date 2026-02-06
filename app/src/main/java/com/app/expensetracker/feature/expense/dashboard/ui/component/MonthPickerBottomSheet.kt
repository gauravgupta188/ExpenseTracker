package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.ui.model.MonthItemUiModel
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPickerBottomSheet(
    selectedMonth: YearMonthUiModel,
    onMonthSelected: (YearMonthUiModel) -> Unit,
    onDismiss: () -> Unit
) {
    val currentYear = Year.now().value
    var year by remember { mutableStateOf(selectedMonth.year) }

    val months = remember(year) {
        (1..12).map { month ->
            val model = YearMonthUiModel(year, month)
            MonthItemUiModel(
                month = month,
                label = Month.of(month)
                    .getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                enabled = !model.isFuture()
            )
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // ---- Year Selector ----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { year-- }
                ) {
                    Icon(Icons.Default.ChevronLeft, null)
                }

                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(
                    onClick = {
                        if (year < currentYear) year++
                    }
                ) {
                    Icon(Icons.Default.ChevronRight, null)
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---- Month Grid ----
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(months) { item ->

                    val isSelected =
                        year == selectedMonth.year &&
                                item.month == selectedMonth.month

                    AssistChip(
                        onClick = {
                            onMonthSelected(
                                YearMonthUiModel(year, item.month)
                            )
                        },
                        enabled = item.enabled,
                        label = {
                            Text(item.label)
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
