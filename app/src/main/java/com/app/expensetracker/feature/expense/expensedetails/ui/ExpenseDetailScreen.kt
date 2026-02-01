package com.app.expensetracker.feature.expense.expensedetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppTopBar
import com.app.expensetracker.core.utils.formatDateTime
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEvent
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiState
import com.app.expensetracker.feature.expense.ui.mapper.displayName

@Composable
fun ExpenseDetailScreen(
    state: ExpenseDetailUiState,
    onEvent: (ExpenseDetailUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Expense Details",
                onBackClick = {
                    onEvent(ExpenseDetailUiEvent.OnBackClicked)
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(ExpenseDetailUiEvent.OnEditClicked)
                        }
                    ) {
                        Icon(Icons.Default.Edit, null)
                    }
                }
            )
        }
    ) { padding ->

        state.expense?.let { expense ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "₹${expense.amount}",
                    style = MaterialTheme.typography.displaySmall
                )

                Text(expense.category.displayName)

                Spacer(Modifier.height(16.dp))

                Text(formatDateTime(expense.date))

                Spacer(Modifier.height(8.dp))

                expense.note?.takeIf { it.isNotBlank() }?.let {
                    Text(it)
                }

                Spacer(Modifier.weight(1f))

                TextButton(
                    onClick = {
                        onEvent(ExpenseDetailUiEvent.OnDeleteClicked)
                    }
                ) {
                    Text(
                        "Delete Expense",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        


    }



}


