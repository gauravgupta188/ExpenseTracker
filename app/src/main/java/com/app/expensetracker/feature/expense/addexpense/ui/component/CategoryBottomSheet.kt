package com.app.expensetracker.feature.expense.addexpense.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    selected: ExpenseCategory?,
    onSelect: (ExpenseCategory) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
       // dragHandle = { ModalBottomSheetDragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Select Category",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                items(ExpenseCategory.values()) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = category == selected,
                        onClick = { onSelect(category) }
                    )
                }
            }
        }
    }
}
