package com.app.expensetracker.feature.expense.monthlyexpense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.usecase.GetExpensesByMonthUseCase
import com.app.expensetracker.feature.expense.monthlyexpense.state.MonthlyExpensesUiEffect
import com.app.expensetracker.feature.expense.monthlyexpense.state.MonthlyExpensesUiEvent
import com.app.expensetracker.feature.expense.monthlyexpense.state.MonthlyExpensesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyExpensesViewModel @Inject constructor(
    private val getExpensesByMonth: GetExpensesByMonthUseCase
) : ViewModel() {

    private val _selectedMonth =
        MutableStateFlow(YearMonthUiModel.current())

    private val _uiState =
        MutableStateFlow(
            MonthlyExpensesUiState(
                selectedMonth = _selectedMonth.value,
                isLoading = true
            )
        )
    val uiState: StateFlow<MonthlyExpensesUiState> =
        _uiState.asStateFlow()

    private val _uiEffect =
        MutableSharedFlow<MonthlyExpensesUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

   /* init {
        observeExpenses()
    }*/

    // -------------------------
    // Public API
    // -------------------------

    fun onEvent(event: MonthlyExpensesUiEvent) {
        when (event) {

            MonthlyExpensesUiEvent.OnBackClicked ->
                emitEffect(MonthlyExpensesUiEffect.NavigateBack)

            MonthlyExpensesUiEvent.OnAddExpenseClicked ->
                emitEffect(MonthlyExpensesUiEffect.NavigateToAddExpense)

            is MonthlyExpensesUiEvent.OnExpenseClicked ->
                emitEffect(
                    MonthlyExpensesUiEffect.NavigateToExpenseDetail(
                        event.expenseId
                    )
                )

            is MonthlyExpensesUiEvent.OnMonthChanged -> {
                _selectedMonth.value = event.month
                _uiState.update {
                    it.copy(
                        selectedMonth = event.month,
                        isLoading = true
                    )
                }
            }
        }
    }

    // -------------------------
    // Core logic
    // -------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
     fun observeExpenses(monthFlow: StateFlow<YearMonthUiModel>) {
        monthFlow
            .flatMapLatest { month ->
                getExpensesByMonth(
                    year = month.year,
                    month = month.month
                )
            }
            .onEach { expenses ->
                _uiState.update {
                    it.copy(
                        expenses = expenses,
                        groupedExpenses = expenses.groupBy { it.date },
                        totalAmount = expenses.sumOf { e -> e.amount },
                        expenseCount = expenses.size,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .catch { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to load expenses"
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    // -------------------------
    // Helpers
    // -------------------------

    private fun emitEffect(effect: MonthlyExpensesUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
