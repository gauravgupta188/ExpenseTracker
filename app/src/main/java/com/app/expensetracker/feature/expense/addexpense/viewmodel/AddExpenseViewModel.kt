package com.app.expensetracker.feature.expense.addexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.usecase.AddExpenseUseCase
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEffect
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<AddExpenseUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: AddExpenseUiEvent) {
        when (event) {
            is AddExpenseUiEvent.AmountChanged ->
                _uiState.update { it.copy(amount = event.value) }

            is AddExpenseUiEvent.CategorySelected ->
                _uiState.update { it.copy(selectedCategory = event.category) }

            is AddExpenseUiEvent.NoteChanged ->
                _uiState.update { it.copy(note = event.value) }

            AddExpenseUiEvent.SaveClicked ->
                saveExpense()
        }
    }

    private fun saveExpense() {
        val state = _uiState.value

        if (state.amount.isBlank()) {
            emitError("Amount is required")
            return
        }

        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            emitError("Enter a valid amount")
            return
        }

        if (state.selectedCategory == null) {
            emitError("Select a category")
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                addExpenseUseCase(
                    Expense(
                        title = state.selectedCategory.value,
                        amount = amount,
                        category = state.selectedCategory,
                        note = state.note,
                        paymentMode = "UNKNOWN",
                        date = state.selectedDate,
                        month = state.selectedDate.monthValue,
                        year = state.selectedDate.year
                    )
                )
            }.onSuccess {
                _uiEffect.emit(AddExpenseUiEffect.NavigateBack)
            }.onFailure {
                emitError("Failed to save expense")
            }
        }
    }

    private fun emitError(message: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = false) }
            _uiEffect.emit(AddExpenseUiEffect.ShowSnackBar(message))
        }
    }
}