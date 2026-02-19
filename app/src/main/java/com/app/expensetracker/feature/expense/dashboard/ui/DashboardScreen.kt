package com.app.expensetracker.feature.expense.dashboard.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.dashboard.ui.component.AddExpenseFab
import com.app.expensetracker.feature.expense.dashboard.ui.component.CategorySection
import com.app.expensetracker.feature.expense.component.ExpenseSectionHeader
import com.app.expensetracker.feature.expense.dashboard.ui.component.DashboardTopAppBar
import com.app.expensetracker.feature.expense.component.ExpenseItem
import com.app.expensetracker.feature.expense.dashboard.ui.component.ExpenseItemDivider
import com.app.expensetracker.feature.expense.dashboard.ui.component.MonthHighlightsSection
import com.app.expensetracker.feature.expense.dashboard.ui.component.MonthPickerBottomSheet
import com.app.expensetracker.feature.expense.dashboard.ui.component.MonthlySnapshotCard
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.ui.state.AppDateEvent
import com.app.expensetracker.feature.expense.ui.state.AppDateUiState
import com.app.expensetracker.feature.expense.viewmodel.AppDateViewModel
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: ExpenseUiState,
    onEvent: (ExpenseUiEvent) -> Unit,
    onAddExpenseClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onSettingsClick: () -> Unit,
    appDateUiState: AppDateUiState,
    onDateEvent: (AppDateEvent) -> Unit,
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DashboardTopAppBar(
                scrollBehavior = scrollBehavior,
                title = "Hello kumar",
                subtitle = appDateUiState.selectedMonth.label,
                monthSelectorClick = {
                    onDateEvent(AppDateEvent.OpenPicker)
                },
                settlingClick = {
                    onSettingsClick()
                }
            )
        },

        floatingActionButton = {
            AddExpenseFab(
                onAddExpenseClick = onAddExpenseClick
            )
        }

    ) { paddingValues ->

        LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .background(Color.White)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 96.dp)

            ) {

            item {
                MonthlySnapshotCard(
                    // modifier = Modifier.padding(horizontal = 16.dp),
                    spend = state.totalAmount,
                    remaining = state.remainingBudget,
                    monthlyBudget = state.monthlyBudget,
                    currencyItem = state.currency,
                    onBudgetEditClick = { onEvent(ExpenseUiEvent.OnViewAllCategoriesClicked) }
                )
            }

            if (!state.topCategories.isEmpty()) {
                item {
                    CategorySection(
                        categories = state.topCategories,
                        currencyItem = state.currency,
                        onCategoryClick = { category ->

                            onEvent(ExpenseUiEvent.OnCategoryClicked(category))

                        },
                        onViewAllClick = {
                            onEvent(ExpenseUiEvent.OnViewAllCategoriesClicked)
                        }
                    )
                }

            }


            if (!state.recentExpenses.isEmpty()) {
                item {
                    ExpenseSectionHeader(
                        onViewAllClick = onViewAllClick,
                        title = "Recent Expenses"
                    )
                }
                items(state.recentExpenses.size) { index ->
                    ExpenseItem(
                        expense = state.recentExpenses[index],
                        currencyItem = state.currency,
                        onClick = {
                            onEvent(ExpenseUiEvent.ExpenseClicked(expense = state.recentExpenses[index]))
                        }
                    )
                }
            }

            if(state.monthHighlights != null) {
                item {
                    state.monthHighlights?.let {
                        MonthHighlightsSection(
                            highlights = it,
                        )
                    }
                }
            }

        }
        }

    if (appDateUiState.isPickerVisible) {
        MonthPickerBottomSheet(
            selectedMonth = appDateUiState.selectedMonth,
            onMonthSelected = { month ->
                onDateEvent(AppDateEvent.MonthSelected(month))
            },
            onDismiss = {
                onDateEvent(AppDateEvent.ClosePicker)
            }
        )
    }
    }

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MonthlySnapshotCard(
        spend = 42850.0,
        remaining = 12150.0,
        monthlyBudget = 60000.0,
        currencyItem = CurrencyProvider.getCurrencyByCode("INR"),
        onBudgetEditClick = {  }
    )
}












