package com.app.expensetracker.feature.expense.addexpense.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEffect
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiState
import com.app.expensetracker.feature.expense.addexpense.ui.component.AmountInput
import com.app.expensetracker.feature.expense.addexpense.ui.component.CategorySelector
import com.app.expensetracker.feature.expense.addexpense.ui.component.NoteInput
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onBack: () -> Unit,
    uiState: AddExpenseUiState,
    onEvent: (AddExpenseUiEvent) -> Unit,
    uiEffect: Flow<AddExpenseUiEffect>,
) {
   // val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                AddExpenseUiEffect.NavigateBack -> onBack()
                is AddExpenseUiEffect.ShowSnackBar ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Expense") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Button(
                onClick = {
                    onEvent(AddExpenseUiEvent.SaveClicked)
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Text("Save Expense")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            AmountInput(
                value = uiState.amount,
                onValueChange = {
                    onEvent(
                        AddExpenseUiEvent.AmountChanged(it)
                    )
                }
            )

            Spacer(Modifier.height(24.dp))

            CategorySelector(
                selected = uiState.selectedCategory,
                onSelect = {
                    onEvent(
                        AddExpenseUiEvent.CategorySelected(it)
                    )
                }
            )

            Spacer(Modifier.height(24.dp))

            NoteInput(
                value = uiState.note,
                onValueChange = {
                    onEvent(
                        AddExpenseUiEvent.NoteChanged(it)
                    )
                }
            )
        }
    }
}
