package com.app.expensetracker.feature.expense.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.ui.state.AppDateEvent
import com.app.expensetracker.feature.expense.ui.state.AppDateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class AppDateViewModel @Inject constructor() : ViewModel() {

    private val _selectedMonth =
        MutableStateFlow(YearMonthUiModel.current())



    private val _uiState = MutableStateFlow(AppDateUiState(selectedMonth = YearMonthUiModel.current()))

    val uiState: StateFlow<AppDateUiState> = _uiState

    val selectedMonth: StateFlow<YearMonthUiModel> =
        uiState.map { it.selectedMonth }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = uiState.value.selectedMonth
            )

    // -------------------------
    // Public API
    // -------------------------

    fun onEvent(event: AppDateEvent) {
    when(event){
        AppDateEvent.ApplySelection -> {}
        AppDateEvent.ClosePicker -> _uiState.update { it.copy(isPickerVisible = false) }
        is AppDateEvent.MonthSelected -> {
            Log.d("Event",event.month.toString())
            _uiState.update { it.copy(selectedMonth = event.month, isPickerVisible = false) }}
        AppDateEvent.NextYear -> {}
        AppDateEvent.OpenPicker -> _uiState.update { it.copy(isPickerVisible = true) }
        AppDateEvent.PreviousYear -> {}
    }

    }

    fun updateMonth(month: YearMonthUiModel) {
        if (!month.isFuture()) {
            _selectedMonth.value = month
        }
    }

    fun moveToPreviousMonth() {
        _selectedMonth.value =
            _selectedMonth.value.previous()
    }

    fun moveToNextMonth() {
        val next = _selectedMonth.value.next()
        if (!next.isFuture()) {
            _selectedMonth.value = next
        }
    }

    fun resetToCurrentMonth() {
        _selectedMonth.value =
            YearMonthUiModel.current()
    }
}
