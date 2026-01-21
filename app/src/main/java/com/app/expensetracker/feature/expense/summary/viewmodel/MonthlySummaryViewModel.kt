package com.app.expensetracker.feature.expense.summary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.usecase.GetMonthlySummaryUseCase
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEffect
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEvent
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MonthlySummaryViewModel @Inject constructor(
    private val getMonthlySummaryUseCase: GetMonthlySummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MonthlySummaryUiState(
            selectedMonth = YearMonthUiModel.current(),
            isLoading = true
        )
    )
    val uiState: StateFlow<MonthlySummaryUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<MonthlySummaryUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private var summaryJob: Job? = null

    init {
        loadMonth(_uiState.value.selectedMonth)
    }

    fun onEvent(event: MonthlySummaryUiEvent) {
        when (event) {

            MonthlySummaryUiEvent.OnBackClicked ->
                emitEffect(MonthlySummaryUiEffect.NavigateBack)

            MonthlySummaryUiEvent.OnAddExpenseClicked ->
                emitEffect(MonthlySummaryUiEffect.NavigateToAddExpense)

            MonthlySummaryUiEvent.OnMonthSelectorClicked ->
                emitEffect(MonthlySummaryUiEffect.OpenMonthPicker)

            is MonthlySummaryUiEvent.OnMonthSelected -> {
                _uiState.update {
                    it.copy(
                        selectedMonth = event.month,
                        isLoading = true,
                        errorMessage = null
                    )
                }
                loadMonth(event.month)
            }
        }
    }

    private fun loadMonth(month: YearMonthUiModel) {
        summaryJob?.cancel()

        summaryJob = viewModelScope.launch {
            getMonthlySummaryUseCase(
                year = month.year,
                month = month.month
            ).collect { categories ->

                val totalSpent = categories.sumOf { it.spentAmount }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        categories = categories,
                        totalSpent = totalSpent,
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun emitEffect(effect: MonthlySummaryUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
