package com.app.expensetracker.feature.expense.summary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.component.ExpenseSectionHeader
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEvent
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiState
import com.app.expensetracker.feature.expense.summary.ui.component.CategoryBreakdownItem
import com.app.expensetracker.feature.expense.summary.ui.component.EditCategoryBudgetSheet
import com.app.expensetracker.feature.expense.summary.ui.component.EditMonthlyBudgetSheet
import com.app.expensetracker.feature.expense.summary.ui.component.SpendingInsightCard
import com.app.expensetracker.feature.expense.summary.ui.component.SummaryAppBar
import com.app.expensetracker.feature.expense.summary.ui.component.SummaryStatsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlySummaryScreen(
    uiState: MonthlySummaryUiState,
    onEvent: (MonthlySummaryUiEvent) -> Unit,
    onBackClick: () -> Unit,
    onMonthSelectorClick: () -> Unit,
    onCategoryClick: (CategorySummaryUiModel) -> Unit,
    onAddExpenseClick: () -> Unit,
    onViewAllCategoriesClick: () -> Unit,
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

            SummaryAppBar(
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior,
                subtitle = uiState.selectedMonth.label,

                )


        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {

            /* ---------- STATS ---------- */
            item {
                SummaryStatsSection(
                    totalSpent = uiState.totalSpent,
                    remainingAmount = uiState.remainingAmount,
                    budgetAmount = uiState.monthlyBudget,
                    spendingChangePercent = uiState.spendingChangePercent,
                    isSpendingDown = uiState.isSpendingDown,
                    onBudgetEditClick = {
                        onEvent(MonthlySummaryUiEvent.BudgetEditClicked)
                    }
                )
            }

            /* ---------- INSIGHT ---------- */
            if (uiState.insightMessage.isNotBlank()) {
                item {
                    SpendingInsightCard(
                        message = uiState.insightMessage
                    )
                }
            }

            /* ---------- CATEGORY HEADER ---------- */
            item {
                Box() {
                    ExpenseSectionHeader(
                        onViewAllClick = onViewAllCategoriesClick,
                        title = "Category Breakdown"
                    )
                }
            }

            /* ---------- CATEGORY LIST ---------- */
            items(
                items = uiState.categories,
                key = { it.category.name }
            ) { category ->
                CategoryBreakdownItem(
                    model = category,
                    onCategoryClick = {
                        onCategoryClick(category)
                    },
                    onEditClick = {
                        onEvent(MonthlySummaryUiEvent.OnCategoryClicked(category))
                    },

                    )
            }

            /* ---------- LOADING ---------- */
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            /* ---------- ERROR ---------- */
            uiState.errorMessage?.let { error ->
                item {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)

                    )
                }
            }
        }

        // ✅ Bottom sheet at ROOT level
        if (uiState.showMonthlyBudgetSheet) {
            EditMonthlyBudgetSheet(
                currentBudget = uiState.monthlyBudget,
                onSave = {
                    onEvent(MonthlySummaryUiEvent.OnSaveBudget(it))
                },
                onDismiss = {
                    onEvent(MonthlySummaryUiEvent.CloseBudgetSheet)
                }
            )
        }

        if (uiState.showCategoryBudgetSheet) {
            EditCategoryBudgetSheet(
                category = uiState.editingCategory!!.category,
                currentBudget = uiState.editingCategory!!.budgetAmount,
                onSave = { amount ->
                    onEvent(
                        MonthlySummaryUiEvent.OnSaveCategoryBudget(amount)
                    )
                },
                onDismiss = {
                    onEvent(MonthlySummaryUiEvent.OnDismissBottomSheet)
                }
            )
        }

    }


}

