package com.app.expensetracker.feature.expense.viewmodel
import androidx.lifecycle.ViewModel
import com.app.expensetracker.core.di.SelectedMonthFlow
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.ui.state.AppDateEvent
import com.app.expensetracker.feature.expense.ui.state.AppDateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppDateViewModel @Inject constructor(
    @SelectedMonthFlow private val sharedMonthFlow: MutableStateFlow<YearMonthUiModel>
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AppDateUiState(selectedMonth = sharedMonthFlow.value)
    )
    val uiState: StateFlow<AppDateUiState> = _uiState.asStateFlow()

    /**
     * Expose the shared flow as read-only so the NavGraph / other
     * screens can observe it without being able to mutate it.
     */
    val selectedMonth: StateFlow<YearMonthUiModel> = sharedMonthFlow.asStateFlow()

    fun onEvent(event: AppDateEvent) {
        when (event) {
            is AppDateEvent.MonthSelected -> {
                if (!event.month.isFuture()) {
                    // Write to the shared flow — DashboardViewModel reacts automatically
                    sharedMonthFlow.value = event.month
                    _uiState.update {
                        it.copy(selectedMonth = event.month, isPickerVisible = false)
                    }
                }
            }
            AppDateEvent.OpenPicker ->
                _uiState.update { it.copy(isPickerVisible = true) }

            AppDateEvent.ClosePicker ->
                _uiState.update { it.copy(isPickerVisible = false) }

            AppDateEvent.ApplySelection -> { /* no-op */ }
            AppDateEvent.NextYear       -> { /* no-op */ }
            AppDateEvent.PreviousYear   -> { /* no-op */ }
        }
    }
}
