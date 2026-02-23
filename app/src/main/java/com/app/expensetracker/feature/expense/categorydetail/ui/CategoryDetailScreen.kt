package com.app.expensetracker.feature.expense.categorydetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiEvent
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiState
import com.app.expensetracker.feature.expense.categorydetail.ui.components.CategoryBudgetCard
import com.app.expensetracker.feature.expense.categorydetail.ui.components.CategoryDetailAppBar
import com.app.expensetracker.feature.expense.categorydetail.ui.components.CategoryExpenseItem
import com.app.expensetracker.feature.expense.component.ExpenseItem
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.ui.component.ExpenseItemDivider
import com.app.expensetracker.feature.expense.dashboard.ui.model.color
import com.app.expensetracker.feature.expense.domain.utils.monthlyUsageLevel
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEvent
import com.app.expensetracker.feature.expense.summary.ui.component.EditCategoryBudgetSheet
import com.app.expensetracker.feature.expense.ui.mapper.color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    state: CategoryDetailUiState,
    onEvent: (CategoryDetailUiEvent) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CategoryDetailAppBar(
                state.category,
                onBackClick = { onEvent(CategoryDetailUiEvent.OnBackClicked) },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)

        ) {

            item {
                CategoryBudgetCard(
                    totalSpent = state.totalSpent,
                    budget = state.budgetAmount,
                    progress = state.progress,
                    currencyItem = state.currency,
                    budgetUsageColor = monthlyUsageLevel(state.totalSpent, state.budgetAmount).color(),
                    onEditClick = {
                        onEvent(CategoryDetailUiEvent.OnCategoryClicked)
                    }
                )
            }

            item {
                CategoryDateRange(
                    yearMonth = state.yearMonth,
                    onFilterClick = {
                        onEvent(CategoryDetailUiEvent.OnFilterClicked)
                    }
                )
                Spacer(Modifier.height(8.dp))
            }

            items(state.expenses) { expense ->
                ExpenseItem(expense = expense,
                    currencyItem = state.currency,
                    onClick = {
                        onEvent(CategoryDetailUiEvent.ExpenseClicked(expense = expense))
                    }
                )
            }
        }

        if (state.showCategoryBudgetSheet) {
            EditCategoryBudgetSheet(
                category = state.category,
                currentBudget = state.budgetAmount,
                onSave = { amount ->
                    onEvent(
                        CategoryDetailUiEvent.OnSaveCategoryBudget(amount)
                    )
                },
                onDismiss = {
                    onEvent(CategoryDetailUiEvent.OnDismissBottomSheet)
                }
            )
        }
    }
}
