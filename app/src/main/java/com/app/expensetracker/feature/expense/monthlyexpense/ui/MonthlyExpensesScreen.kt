package com.app.expensetracker.feature.expense.monthlyexpense.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.expensetracker.core.components.AppTopBar
import com.app.expensetracker.feature.expense.component.ExpenseItem
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.monthlyexpense.ui.component.DateHeader
import com.app.expensetracker.feature.expense.monthlyexpense.ui.component.MonthSummaryStrip

@Composable
fun MonthlyExpensesScreen(
    state: MonthlyExpensesUiState,
    onBackClick: () -> Unit,
    onExpenseClick: (Expense) -> Unit,
    onAddExpenseClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = state.month.label,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            item {
                MonthSummaryStrip(
                    total = state.totalAmount,
                    count = state.groupedExpenses.values.sumOf { it.size }
                )
            }

            state.groupedExpenses.forEach { (date, expenses) ->

                item {
                    DateHeader(
                        label = date.formatFriendly()
                    )
                }

                items(expenses, key = { it.id }) { expense ->
                    ExpenseItem(
                        expense = expense,
                        onClick = { onExpenseClick(expense) }
                    )
                }
            }
        }
    }
}
