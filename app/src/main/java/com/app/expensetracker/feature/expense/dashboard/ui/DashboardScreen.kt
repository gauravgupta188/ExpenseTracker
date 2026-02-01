package com.app.expensetracker.feature.expense.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.dashboard.ui.component.AddExpenseFab
import com.app.expensetracker.feature.expense.dashboard.ui.component.CategorySection
import com.app.expensetracker.feature.expense.component.ExpenseSectionHeader
import com.app.expensetracker.feature.expense.dashboard.ui.component.DashboardTopAppBar
import com.app.expensetracker.feature.expense.component.ExpenseItem
import com.app.expensetracker.feature.expense.dashboard.ui.component.MonthlySnapshotCard
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: ExpenseUiState,
    onEvent: (ExpenseUiEvent) -> Unit,
    onAddExpenseClick: () -> Unit,
    onViewAllClick: () -> Unit,
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DashboardTopAppBar(scrollBehavior = scrollBehavior)
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
                contentPadding = PaddingValues(16.dp)

            ) {

                item {
                    Spacer(Modifier.height(8.dp))

                    MonthlySnapshotCard(
                       // modifier = Modifier.padding(horizontal = 16.dp),
                        spend = state.totalAmount,
                        remaining = state.remainingBudget,
                        monthlyBudget = state.monthlyBudget,
                        onBudgetEditClick = {  onEvent(ExpenseUiEvent.OnViewAllCategoriesClicked)}
                    )
                }

                if (!state.topCategories.isEmpty()) {
                    item {
                        CategorySection(
                            categories = state.topCategories,
                            onCategoryClick = { category ->

                                onEvent(ExpenseUiEvent.OnCategoryClicked(category))

                            },
                            onViewAllClick = {
                               onEvent(ExpenseUiEvent.OnViewAllCategoriesClicked)
                            }
                        )
                    }

                }
                item {
                    ExpenseSectionHeader(
                        onViewAllClick = onViewAllClick,
                        title = "Recent Expenses"
                    )
                }

                items(state.recentExpenses.size) { index ->
                    ExpenseItem(expense = state.recentExpenses[index],
                        onClick = {
                            onEvent(ExpenseUiEvent.ExpenseClicked(expense = state.recentExpenses[index]))
                        }
                        )
                }
            }
        }
    }












