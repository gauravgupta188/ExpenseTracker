package com.app.expensetracker.feature.expense.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.core.currency.CurrencyManager
import com.app.expensetracker.core.utils.extractFirstName
import com.app.expensetracker.core.utils.generateMonths
import com.app.expensetracker.feature.auth.domain.repository.AuthRepository
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect.NavigateToAllCategories
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect.NavigateToCategory
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect.NavigateToExpenseDetail
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect.NavigateToMonthlyExpenses
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.data.mapper.mapToMonthHighlightsUi
import com.app.expensetracker.core.di.SelectedMonthFlow
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.usecase.GetExpensesByMonthUseCase
import com.app.expensetracker.feature.expense.domain.usecase.GetRecentExpenseByMonthUseCase
import com.app.expensetracker.feature.expense.domain.usecase.ObserveMonthlyBudgetUseCase
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Year
import javax.inject.Inject

/**
 * DashboardViewModel — self-contained, reactive, offline-first.
 *
 * Key design decisions:
 *  1. Receives selectedMonth as a constructor-injected StateFlow so it
 *     stays in sync with AppDateViewModel without coupling to the NavGraph.
 *  2. observeDashboardData() is called in init{} — never from the UI layer.
 *  3. All data flows come from Room (via the repository) so the UI loads
 *     instantly even with no network connection.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getExpensesByMonth: GetExpensesByMonthUseCase,
    private val observeMonthlyBudget: ObserveMonthlyBudgetUseCase,
    private val getRecentExpenses: GetRecentExpenseByMonthUseCase,
    private val authRepository: AuthRepository,
    private val currencyManager: CurrencyManager,
    @SelectedMonthFlow private val selectedMonthFlow: StateFlow<YearMonthUiModel>
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<ExpenseUiEffect>()
    val uiEffect: SharedFlow<ExpenseUiEffect> = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(
        ExpenseUiState(
            months = generateMonths(startYear = 2025, endYear = Year.now().value),
            selectedMonth = selectedMonthFlow.value,
            isLoading = true
        )
    )
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    init {
        observeDashboardData()   // ← called here, never from the NavGraph
        observeCurrency()
        loadUser()
    }

    // ── Event handler ──────────────────────────────────────────────────────

    fun onEvent(event: ExpenseUiEvent) {
        when (event) {
            is ExpenseUiEvent.ExpenseClicked ->
                emitEffect(NavigateToExpenseDetail(event.expense))

            is ExpenseUiEvent.OnCategoryClicked ->
                emitEffect(NavigateToCategory(event.category))

            ExpenseUiEvent.OnViewAllCategoriesClicked ->
                emitEffect(NavigateToAllCategories)

            ExpenseUiEvent.AddExpenseClicked -> {
                /* handled by NavGraph callback */
            }

            is ExpenseUiEvent.OnMonthSelected ->
                _uiState.update { it.copy(showMonthPicker = true) }

            ExpenseUiEvent.DismissMonthPicker ->
                _uiState.update { it.copy(showMonthPicker = false) }

            is ExpenseUiEvent.DeleteExpense -> {
                /* TODO: wire up DeleteExpenseUseCase */
            }
        }
    }

    // ── Core data pipeline ─────────────────────────────────────────────────

    /**
     * Observes the selected month and re-subscribes to the appropriate
     * data streams every time the month changes.
     *
     * flatMapLatest cancels the previous combine block automatically when
     * a new month is selected — no manual Job tracking needed.
     *
     * The two branches (current month / past month) load different data:
     *   • Current month → recent expenses + budget  (no comparison needed)
     *   • Past month    → all categories + previous month total for highlights
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeDashboardData() {
        selectedMonthFlow
            .onEach { month ->
                // Show loading spinner as soon as month changes
                _uiState.update {
                    it.copy(selectedMonth = month, isLoading = true)
                }
            }
            .flatMapLatest { month ->
                val isCurrentMonth = month == YearMonthUiModel.current()

                if (isCurrentMonth) {
                    buildCurrentMonthFlow(month)
                } else {
                    buildPastMonthFlow(month)
                }
            }
            .onEach { state -> _uiState.update { state } }
            .launchIn(viewModelScope)
    }

    // ── Private helpers ────────────────────────────────────────────────────

    /**
     * Current month: show budget overview + recent expenses.
     * No month-over-month comparison (not enough data yet).
     */
    private fun buildCurrentMonthFlow(month: YearMonthUiModel) =
        combine(
            getExpensesByMonth(year = month.year, month = month.month),
            observeMonthlyBudget(year = month.year, month = month.month),
            getRecentExpenses()
        ) { expenses, budget, recent ->
            val totalAmount = expenses.sumOf { it.amount }
            val categoryBudgets = budget?.categoryBudgets.orEmpty()
            val remainingBudget = (budget?.monthlyBudget ?: 0.0) - totalAmount

            val topCategories = expenses
                .groupBy { it.category }
                .map { (category, list) ->
                    CategorySummaryUiModel(
                        category = category,
                        spentAmount = list.sumOf { it.amount },
                        budgetAmount = categoryBudgets[category]
                    )
                }
                .sortedByDescending { it.spentAmount }
                .take(3)

            _uiState.value.copy(
                expenses = expenses,
                recentExpenses = recent,
                totalAmount = totalAmount,
                monthlyBudget = budget?.monthlyBudget ?: 0.0,
                remainingBudget = remainingBudget,
                topCategories = topCategories,
                allCategories = null,
                monthHighlights = null,
                selectedMonth = month,
                isLoading = false,
                errorMessage = null
            )
        }

    /**
     * Past month: show full category breakdown + month-over-month highlights.
     * Loads previous month's total for percentage comparison.
     */
    private fun buildPastMonthFlow(month: YearMonthUiModel): kotlinx.coroutines.flow.Flow<ExpenseUiState> {
        val previousMonth = month.previous()
        return combine(
            getExpensesByMonth(year = month.year, month = month.month),
            getExpensesByMonth(year = previousMonth.year, month = previousMonth.month),
            observeMonthlyBudget(year = month.year, month = month.month)
        ) { expenses, previousExpenses, budget ->
            val totalAmount = expenses.sumOf { it.amount }
            val categoryBudgets = budget?.categoryBudgets.orEmpty()
            val remainingBudget = (budget?.monthlyBudget ?: 0.0) - totalAmount
            val previousMonthTotal = previousExpenses.sumOf { it.amount }

            val allCategories = expenses
                .groupBy { it.category }
                .map { (category, list) ->
                    CategorySummaryUiModel(
                        category = category,
                        spentAmount = list.sumOf { it.amount },
                        budgetAmount = categoryBudgets[category]
                    )
                }
                .sortedByDescending { it.spentAmount }

            val highlights = mapToMonthHighlightsUi(
                currentMonthCategories = allCategories,
                previousMonthTotal = previousMonthTotal,
                currentMonthTotal = totalAmount
            )

            _uiState.value.copy(
                expenses = expenses,
                recentExpenses = emptyList(),
                totalAmount = totalAmount,
                monthlyBudget = budget?.monthlyBudget ?: 0.0,
                remainingBudget = remainingBudget,
                topCategories = allCategories.take(3),
                allCategories = allCategories,
                monthHighlights = highlights,
                selectedMonth = month,
                isLoading = false,
                errorMessage = null
            )
        }
    }

    private fun loadUser() {
        val user = authRepository.getCurrentUser()
        val name = extractFirstName(user?.displayName)
            ?: user?.email?.substringBefore("@")
            ?: ""
        _uiState.update { it.copy(currentUser = user, displayName = name) }
    }

    private fun observeCurrency() {
        viewModelScope.launch {
            currencyManager.currency.collect { currencyItem ->
                _uiState.update { it.copy(currency = currencyItem) }
            }
        }
    }

    private fun emitEffect(effect: ExpenseUiEffect) {
        viewModelScope.launch { _uiEffect.emit(effect) }
    }
}