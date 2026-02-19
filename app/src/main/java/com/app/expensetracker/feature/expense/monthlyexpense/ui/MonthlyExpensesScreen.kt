package com.app.expensetracker.feature.expense.monthlyexpense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.utils.formatDateTime
import com.app.expensetracker.feature.expense.component.ExpenseItem
import com.app.expensetracker.feature.expense.dashboard.ui.component.AddExpenseFab
import com.app.expensetracker.feature.expense.dashboard.ui.component.ExpenseItemDivider
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.monthlyexpense.state.MonthlyExpensesUiState
import com.app.expensetracker.feature.expense.monthlyexpense.ui.component.DateHeader
import com.app.expensetracker.feature.expense.monthlyexpense.ui.component.MonthlyExpenseAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyExpensesScreen(
    state: MonthlyExpensesUiState,
    onBackClick: () -> Unit,
    onExpenseClick: (Expense) -> Unit,
    onAddExpenseClick: () -> Unit
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

            MonthlyExpenseAppBar(
                subtitle = "Monthly Summary",
                expenseCount = "${state.groupedExpenses.values.sumOf { it.size }}",
                currencyItem = state.currency,
                total = "${state.totalAmount}",
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            AddExpenseFab(
                onAddExpenseClick = onAddExpenseClick
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 96.dp)
        ) {

          /*  item {
                MonthSummaryStrip(
                    total = state.totalAmount,
                    count = state.groupedExpenses.values.sumOf { it.size }
                )
            }
*/
            state.groupedExpenses.forEach { (date, expenses) ->

                item {
                    DateHeader(
                        label = formatDateTime(date)
                    )
                }

                items(expenses, key = { it.id }) { expense ->
                    ExpenseItem(
                        expense = expense,
                        currencyItem = state.currency,
                        onClick = { onExpenseClick(expense) }
                    )

                }
            }
        }
    }
}
