package com.app.expensetracker.feature.expense.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.core.currency.CurrencyManager
import com.app.expensetracker.core.utils.generateMonths
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect.*
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.usecase.GetExpensesByMonthUseCase
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.data.mapper.mapToMonthHighlightsUi
import com.app.expensetracker.feature.expense.domain.usecase.ObserveMonthlyBudgetUseCase
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.domain.model.DashboardAggregate
import com.app.expensetracker.feature.expense.domain.usecase.GetRecentExpenseByMonthUseCase
import com.app.expensetracker.feature.expense.viewmodel.AppDateViewModel
import com.app.expensetracker.feature.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Year
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getExpensesByMonth: GetExpensesByMonthUseCase,
    private val observeMonthlyBudget: ObserveMonthlyBudgetUseCase,
    private val getRecentExpenses: GetRecentExpenseByMonthUseCase,
    private val currencyManager: CurrencyManager
) : ViewModel() {

    private val _selectedMonth =
        MutableStateFlow(YearMonthUiModel.current())
    private val _uiEffect = MutableSharedFlow<ExpenseUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val months = generateMonths(
        startYear = 2025,
        endYear = Year.now().value
    )

    private val _uiState =
        MutableStateFlow(
            ExpenseUiState(
                months = months,
                selectedMonth = _selectedMonth.value,
                isLoading = true
            )
        )

    val uiState: StateFlow<ExpenseUiState> =
        _uiState.asStateFlow()

    init {
        observeRecentExpenses()
        observeCurrency()
        //observeDashboardData()
       // updateSelectedMonth()

    }

    fun onMonthSelected(month: YearMonthUiModel) {
        _uiState.update {
            it.copy(
                selectedMonth = month,
                isLoading = true
            )
        }
    }


    fun onEvent(event: ExpenseUiEvent) {
        when (event) {
            is ExpenseUiEvent.DeleteExpense -> {
                /*   viewModelScope.launch {
                       repository.deleteExpense(event.expense)
                   }*/
            }

            ExpenseUiEvent.AddExpenseClicked -> {
                // Handle navigation or UI logic for adding expense
            }

            is ExpenseUiEvent.ExpenseClicked -> {
                emitEffect(NavigateToExpenseDetail(event.expense))
            }

            is ExpenseUiEvent.OnMonthSelected -> {
                _uiState.update { it.copy(showMonthPicker = true) }
            }

            is ExpenseUiEvent.OnCategoryClicked -> {
                emitEffect(
                    NavigateToCategory(event.category)
                )
            }

            ExpenseUiEvent.OnViewAllCategoriesClicked -> {
                emitEffect(
                    NavigateToAllCategories
                )
            }

            ExpenseUiEvent.DismissMonthPicker -> {
                _uiState.update { it.copy(showMonthPicker = false) }
            }
        }
    }

    fun observeDashboardData(monthFlow: StateFlow<YearMonthUiModel>){
        monthFlow.flatMapLatest { month ->
            val isCurrentMonth = monthFlow.value == YearMonthUiModel.current()
            if (isCurrentMonth) {
                combine(
                    getExpensesByMonth(
                        year = month.year,
                        month = month.month
                    ),
                    observeMonthlyBudget(
                        year = month.year,
                        month = month.month
                    ), getRecentExpenses()
                ) { expenses, budget,recent ->
                    val totalAmount = expenses.sumOf { it.amount }
                    var monthlyBudget = budget?.monthlyBudget ?: 0.0
                    val categoryBudgets =
                        budget?.categoryBudgets.orEmpty()

                    var remainingBudget = monthlyBudget - totalAmount


                    val topCategories =
                        expenses
                            .groupBy { it.category }
                            .map { (category, list) ->

                                val spent = list.sumOf { it.amount }
                                val budgetAmount = categoryBudgets[category]

                                CategorySummaryUiModel(
                                    category = category,
                                    spentAmount = spent,
                                    budgetAmount = budgetAmount
                                )
                            }
                            .sortedByDescending { it.spentAmount }
                            .take(3)

                    DashboardAggregate(
                        expenses = expenses,
                        totalAmount = totalAmount,
                        topCategories = topCategories,
                        monthlyBudget = budget?.monthlyBudget,
                        remainingBudget = remainingBudget,
                        allCategories = null,
                        selectedMonth = month,
                        recentExpenses = recent,
                        monthHighlights = null
                    )
                }
            }else{
                val previousMonth = month.previous()
                combine(
                    getExpensesByMonth(
                        year = month.year,
                        month = month.month
                    ),
                    getExpensesByMonth(year = previousMonth.year, month = previousMonth.month),
                    observeMonthlyBudget(
                        year = month.year,
                        month = month.month
                    )
                ) { expenses, previousExpense, budget ->

                    val totalAmount = expenses.sumOf { it.amount }

                    var monthlyBudget = budget?.monthlyBudget ?: 0.0


                    val categoryBudgets =
                        budget?.categoryBudgets.orEmpty()

                    var remainingBudget = monthlyBudget - totalAmount


                    val allCategories =
                        expenses
                            .groupBy { it.category }
                            .map { (category, list) ->

                                val spent = list.sumOf { it.amount }
                                val budgetAmount = categoryBudgets[category]

                                CategorySummaryUiModel(
                                    category = category,
                                    spentAmount = spent,
                                    budgetAmount = budgetAmount
                                )
                            }
                            .sortedByDescending { it.spentAmount }


                    val topCategories = allCategories.take(3)

                    val previousMonthTotal = previousExpense.sumOf { it.amount }

                    val highlightsUi =
                        mapToMonthHighlightsUi(
                            currentMonthCategories = allCategories,
                            previousMonthTotal = previousMonthTotal,
                            currentMonthTotal = totalAmount
                        )

                    DashboardAggregate(
                        expenses = expenses,
                        totalAmount = totalAmount,
                        topCategories = emptyList(),
                        monthlyBudget = budget?.monthlyBudget,
                        remainingBudget = remainingBudget,
                        allCategories = allCategories,
                        selectedMonth = month,
                        monthHighlights = highlightsUi,
                        recentExpenses = emptyList()
                    )
                }
            } .onEach { aggregate ->
                _uiState.update {
                    it.copy(
                        expenses = aggregate.expenses,
                        totalAmount = aggregate.totalAmount,
                        monthlyBudget = aggregate?.monthlyBudget ?: 0.0,
                        remainingBudget = aggregate.remainingBudget,
                        topCategories = aggregate.topCategories,
                        allCategories = null,
                        selectedMonth = aggregate.selectedMonth,
                        recentExpenses = aggregate.recentExpenses,
                        monthHighlights = aggregate.monthHighlights,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeRecentExpenses() {
        getRecentExpenses()
            .onEach { recent ->
                _uiState.update {
                    it.copy(
                        recentExpenses = recent,
                        monthHighlights = null
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeCurrency() {
        viewModelScope.launch {
            currencyManager.currency.collect { currencyItem ->
                _uiState.update {
                    it.copy(currency = currencyItem)
                }
            }
        }
    }

    private fun emitEffect(effect: ExpenseUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

}




