package com.app.expensetracker.feature.expense.summary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEffect
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEvent
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlySummaryViewModel @Inject constructor(
    // later: GetMonthlySummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MonthlySummaryUiState(
            selectedMonth = YearMonthUiModel.current()
        )
    )
    val uiState: StateFlow<MonthlySummaryUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<MonthlySummaryUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: MonthlySummaryUiEvent) {
        when (event) {
            MonthlySummaryUiEvent.OnBackClicked ->
                emitEffect(MonthlySummaryUiEffect.NavigateBack)

            MonthlySummaryUiEvent.OnAddExpenseClicked ->
                emitEffect(MonthlySummaryUiEffect.NavigateToAddExpense)

            MonthlySummaryUiEvent.OnMonthSelectorClicked ->
                emitEffect(MonthlySummaryUiEffect.OpenMonthPicker)
        }
    }

    private fun emitEffect(effect: MonthlySummaryUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
