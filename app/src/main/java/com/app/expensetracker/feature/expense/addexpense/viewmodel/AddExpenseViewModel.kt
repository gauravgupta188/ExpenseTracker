package com.app.expensetracker.feature.expense.addexpense.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.usecase.AddExpenseUseCase
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEffect
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiState
import com.app.expensetracker.feature.expense.addexpense.state.ExpenseFormMode
import com.app.expensetracker.feature.expense.domain.usecase.GetExpenseByIdUseCase
import com.app.expensetracker.feature.expense.domain.usecase.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getExpenseById: GetExpenseByIdUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {
    private val expenseId: String? =
        savedStateHandle["expenseId"]

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<AddExpenseUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()
    private var pendingDate: LocalDate? = null

    init {
        expenseId?.let { setEditMode(it) }

        if (uiState.value.mode is ExpenseFormMode.Edit) {
            expenseId?.let { loadExpense(it) }

        }
    }

    fun setEditMode(expenseId: String) {
        _uiState.update {
            it.copy(mode = ExpenseFormMode.Edit(expenseId))
        }
    }

    fun onEvent(event: AddExpenseUiEvent) {
        when (event) {
            is AddExpenseUiEvent.AmountChanged ->
                _uiState.update { it.copy(amount = event.value) }

            is AddExpenseUiEvent.CategorySelected ->
                _uiState.update { it.copy(selectedCategory = event.category) }

            AddExpenseUiEvent.SeeAllCategoriesClicked -> {
                _uiState.update {
                    it.copy(isCategorySheetVisible = true)
                }
            }

            AddExpenseUiEvent.CloseCategorySheet -> {
                _uiState.update {
                    it.copy(isCategorySheetVisible = false)
                }
            }

            is AddExpenseUiEvent.NoteChanged ->
                _uiState.update { it.copy(note = event.value) }

            AddExpenseUiEvent.SaveClicked ->
                saveExpense()

            AddExpenseUiEvent.DateClicked ->
                emitEffect(AddExpenseUiEffect.ShowDatePicker)


            is AddExpenseUiEvent.DateSelected -> {
                pendingDate = event.date
                emitEffect(AddExpenseUiEffect.ShowTimePicker)
            }

            is AddExpenseUiEvent.TimeSelected -> {
                val date = pendingDate ?: LocalDate.now()
                _uiState.update { it.copy(selectedDate = LocalDateTime.of(date, event.time)) }
            }
        }
    }

    private fun emitEffect(effect: AddExpenseUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

    private fun loadExpense(expenseId: String) {
        viewModelScope.launch {
            getExpenseById(expenseId).collect { expense ->

                _uiState.update {
                    it.copy(
                        amount = expense.amount.toString(),
                        selectedCategory = expense.category,
                        selectedDate = expense.date,
                        note = expense.note ?: ""
                    )
                }
            }


        }
    }

    private fun saveExpense() {
        val state = _uiState.value

        if (state.amount.isBlank()) {
            emitError("Amount is required")
            return
        }

        if (state.note.isBlank()) {
            emitError("Note is required")
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
                if (uiState.value.mode is ExpenseFormMode.Edit) {
                    updateExpenseUseCase(
                        Expense(
                            id = expenseId!!,
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
                } else
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

