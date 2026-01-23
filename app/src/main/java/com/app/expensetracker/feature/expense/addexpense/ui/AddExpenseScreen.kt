package com.app.expensetracker.feature.expense.addexpense.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppDatePickerDialog
import com.app.expensetracker.core.components.AppScaffold
import com.app.expensetracker.core.components.AppTopBar
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEffect
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiState
import com.app.expensetracker.feature.expense.addexpense.ui.component.AmountInput
import com.app.expensetracker.feature.expense.addexpense.ui.component.AppTimePickerDialog
import com.app.expensetracker.feature.expense.addexpense.ui.component.CategoryBottomSheet
import com.app.expensetracker.feature.expense.addexpense.ui.component.CategorySelection
import com.app.expensetracker.feature.expense.addexpense.ui.component.DateInput
import com.app.expensetracker.feature.expense.addexpense.ui.component.NoteInput
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

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
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                AddExpenseUiEffect.NavigateBack -> onBack()
                is AddExpenseUiEffect.ShowSnackBar ->
                    snackbarHostState.showSnackbar(effect.message)

                AddExpenseUiEffect.ShowDatePicker -> showDatePicker = true
                is AddExpenseUiEffect.ShowTimePicker -> showTimePicker = true
            }

        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(title = "Add Expense", onBackClick = onBack)
        },
        snackbarHostState = snackbarHostState,

        bottomBar = {

            Box(modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
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
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                AmountInput(
                    value = uiState.amount,
                    onValueChange = {
                        onEvent(
                            AddExpenseUiEvent.AmountChanged(it)
                        )
                    }
                )
            }

            item {
                CategorySelection(
                    selected = uiState.selectedCategory,
                    onSelect = {
                        onEvent(
                            AddExpenseUiEvent.CategorySelected(it)
                        )
                    },
                    onViewAllClick = { onEvent(AddExpenseUiEvent.SeeAllCategoriesClicked) },

                    )
            }

            item {
                DateInput(
                    date = uiState.selectedDate,
                    onClick = {
                        onEvent(AddExpenseUiEvent.DateClicked)
                    }
                )
            }
            item {
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

        // ✅ Bottom sheet at ROOT level
        if (uiState.isCategorySheetVisible) {
            CategoryBottomSheet(
                selected = uiState.selectedCategory,
                onSelect = {
                    onEvent(AddExpenseUiEvent.CategorySelected(it))
                },
                onDismiss = {
                    onEvent(AddExpenseUiEvent.CloseCategorySheet)
                }
            )
        }

        if (showDatePicker) {
            AppDatePickerDialog(
                initialDate = uiState.selectedDate.toLocalDate(),
                onDateSelected = {
                    onEvent(AddExpenseUiEvent.DateSelected(it))
                    showDatePicker = false
                },
                onDismiss = {
                    showDatePicker = false
                }
            )
        }

        if (showTimePicker) {
            AppTimePickerDialog(
                initialDateTime = uiState.selectedDate.toLocalTime(),
                onTimeSelected = {
                    showTimePicker = false
                    onEvent(AddExpenseUiEvent.TimeSelected(it))
                },
                onDismiss = { showTimePicker = false }
            )
        }


    }
}
