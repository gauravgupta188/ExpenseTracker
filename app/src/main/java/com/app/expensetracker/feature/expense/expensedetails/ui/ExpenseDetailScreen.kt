package com.app.expensetracker.feature.expense.expensedetails.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEffect
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEvent
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiState
import com.app.expensetracker.feature.expense.expensedetails.ui.component.DeleteExpenseAction
import com.app.expensetracker.feature.expense.expensedetails.ui.component.DeleteExpenseConfirmationDialog
import com.app.expensetracker.feature.expense.expensedetails.ui.component.EditExpenseButton
import com.app.expensetracker.feature.expense.expensedetails.ui.component.ExpenseDetailsCard
import com.app.expensetracker.feature.expense.expensedetails.ui.component.ExpenseDetailsTopAppBar
import com.app.expensetracker.feature.expense.expensedetails.ui.component.ExpenseHeroCard
import com.app.expensetracker.feature.expense.ui.mapper.displayName
import kotlinx.coroutines.flow.Flow

@Composable
fun ExpenseDetailScreen(
    state: ExpenseDetailUiState,
    onEvent: (ExpenseDetailUiEvent) -> Unit,
    uiEffect: Flow<ExpenseDetailUiEffect>,
    onBackClick: () -> Unit,
    onEditClick:() -> Unit
    ) {
   /* Scaffold(
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
*/
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is ExpenseDetailUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }

                ExpenseDetailUiEffect.NavigateBack -> {
                   onBackClick()
                }

                is ExpenseDetailUiEffect.NavigateToEdit -> {
                   onEditClick()
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            ExpenseDetailsTopAppBar(
                onBackClick = { onEvent(ExpenseDetailUiEvent.OnBackClicked) },
                onEditClick = { onEvent(ExpenseDetailUiEvent.OnEditClicked) },

            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                ExpenseHeroCard(
                    amount = state.expense?.displayAmount() ?: "",
                    category = state.expense?.category?.displayName ?: "",
                    date = state.expense?.formattedDateTime() ?: "",
                )
            }
            item {
                ExpenseDetailsCard(
                    category = state.expense?.category?.displayName ?: "",
                    date = state.expense?.formattedDateTime() ?: "",
                    note = state.expense?.note ?: ""
                )
            }
            // item { AttachmentSection() }
            item { EditExpenseButton({

                onEvent(ExpenseDetailUiEvent.OnEditClicked)
            }) }
            item { DeleteExpenseAction({
                onEvent(ExpenseDetailUiEvent.OnDeleteClicked)
            }) }
        }

        if (state.isDeleteConfirmationVisible) {
            DeleteExpenseConfirmationDialog(
                expenseTitle = state.expense?.title ?: "",
                onConfirmDelete = {
                    onEvent(ExpenseDetailUiEvent.OnConfirmDelete)
                },
                onDismiss = {
                    onEvent(ExpenseDetailUiEvent.OnDismissDeleteDialog)
                }
            )
        }

    }


}


