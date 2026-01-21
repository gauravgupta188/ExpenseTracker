package com.app.expensetracker.feature.expense.addexpense.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

@Composable
fun CategorySelection(
    selected: ExpenseCategory?,
    onSelect: (ExpenseCategory) -> Unit,
    onViewAllClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                modifier = Modifier.clickable{ onViewAllClick() },
                text = "View All",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth().height(96.dp),
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            items(ExpenseCategory.entries.toTypedArray()) { category ->
                CategoryItem(
                    category = category,
                    isSelected = category == selected,
                    onClick = { onSelect(category) }
                )
            }
        }
    }
}
