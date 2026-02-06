package com.app.expensetracker.feature.expense.expensedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.usecase.DeleteExpenseUseCase
import com.app.expensetracker.feature.expense.domain.usecase.GetExpenseByIdUseCase
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEffect
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEffect.*
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEvent
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiState
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
class ExpenseDetailViewModel @Inject constructor(
    private val getExpenseById: GetExpenseByIdUseCase,
    private val deleteExpense: DeleteExpenseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val expenseId: String =
        savedStateHandle["expenseId"]!!

    private val _uiState =
        MutableStateFlow(
            ExpenseDetailUiState(isLoading = true)
        )
    val uiState: StateFlow<ExpenseDetailUiState> =
        _uiState.asStateFlow()

    private val _uiEffect =
        MutableSharedFlow<ExpenseDetailUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        loadExpense()
    }

    fun onEvent(event: ExpenseDetailUiEvent) {
        when (event) {

            ExpenseDetailUiEvent.OnBackClicked ->
                emitEffect(NavigateBack)

            ExpenseDetailUiEvent.OnEditClicked ->
                emitEffect(
                    NavigateToEdit(expenseId)
                )

            ExpenseDetailUiEvent.OnDeleteClicked -> {
                _uiState.update { it.copy(isDeleteConfirmationVisible = true) }
            }

            ExpenseDetailUiEvent.OnConfirmDelete -> {
                _uiState.update { it.copy(isDeleteConfirmationVisible = false) }
                deleteExpense()

                //  emitEffect(NavigateBack)
            }

            ExpenseDetailUiEvent.OnDismissDeleteDialog -> {
                _uiState.update { it.copy(isDeleteConfirmationVisible = false) }
            }
        }
    }

    private fun loadExpense() {
        viewModelScope.launch {
            try {
               getExpenseById(expenseId).collect { expense ->
                   _uiState.update {
                       it.copy(
                           expense = expense,
                           isLoading = false
                       )
                   }
               }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load expense"
                    )
                }
            }
        }
    }

    private fun deleteExpense() {
        viewModelScope.launch {
            runCatching {
                deleteExpense(expenseId)

            }.onSuccess {
                emitEffect(NavigateBack)
            }
                .onFailure {
                    emitEffect(
                        ShowError(
                            "Failed to delete expense"
                        )
                    )
                }
        }
    }

    private fun emitEffect(effect: ExpenseDetailUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
