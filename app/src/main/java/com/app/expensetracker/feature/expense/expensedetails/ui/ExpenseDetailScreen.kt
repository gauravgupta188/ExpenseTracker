package com.app.expensetracker.feature.expense.expensedetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppButton
import com.app.expensetracker.core.components.AppScaffold
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEffect
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEvent
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiState
import com.app.expensetracker.feature.expense.expensedetails.ui.component.DeleteExpenseAction
import com.app.expensetracker.feature.expense.expensedetails.ui.component.DeleteExpenseConfirmationDialog
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
    AppScaffold (
        snackbarHostState = snackbarHostState,
        topBar = {
            ExpenseDetailsTopAppBar(
                onBackClick = { onEvent(ExpenseDetailUiEvent.OnBackClicked) },
                onEditClick = { onEvent(ExpenseDetailUiEvent.OnEditClicked) },
            )
        },

        bottomBar = {
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .navigationBarsPadding() // 🔥 important
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {

                    Column() {
                        AppButton("Edit Expense",{
                            onEvent(ExpenseDetailUiEvent.OnEditClicked)
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                        DeleteExpenseAction({
                            onEvent(ExpenseDetailUiEvent.OnDeleteClicked)
                        })
                    }

                }
            }
        }


    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) .background(color = MaterialTheme.colorScheme.onPrimary),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
        ) {
            item {
                val amountWithCurrency = "${state.currency.symbol} ${state.expense?.displayAmount() ?: ""}"
                ExpenseHeroCard(
                    amount =  amountWithCurrency,
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


