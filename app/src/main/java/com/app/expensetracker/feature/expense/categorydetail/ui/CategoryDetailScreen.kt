package com.app.expensetracker.feature.expense.categorydetail.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.expensetracker.core.components.AppScaffold
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiEvent
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiState
import com.app.expensetracker.feature.expense.categorydetail.ui.components.CategoryBudgetCard
import com.app.expensetracker.feature.expense.categorydetail.ui.components.CategoryDetailHeader
import com.app.expensetracker.feature.expense.categorydetail.ui.components.CategoryExpenseItem

@Composable
fun CategoryDetailScreen(
    state: CategoryDetailUiState,
    onEvent: (CategoryDetailUiEvent) -> Unit
) {
    AppScaffold(
       /* floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(CategoryDetailUiEvent.OnAddExpenseClicked) }
            ) {
                Icon(Icons.Default.Add, null)
            }
        }*/
    ) { padding ->

        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            item {
                CategoryDetailHeader(
                    category = state.category,
                    onBack = { onEvent(CategoryDetailUiEvent.OnBackClicked) }
                )
            }

            item {
                CategoryBudgetCard(
                    totalSpent = state.totalSpent,
                    budget = state.budgetAmount,
                    progress = state.progress
                )
            }

            item {
                CategoryDateRange(
                    yearMonth = state.yearMonth,
                    onFilterClick = {
                        onEvent(CategoryDetailUiEvent.OnFilterClicked)
                    }
                )
            }

            items(state.expenses) { expense ->
                CategoryExpenseItem(expense)
            }
        }
    }
}
