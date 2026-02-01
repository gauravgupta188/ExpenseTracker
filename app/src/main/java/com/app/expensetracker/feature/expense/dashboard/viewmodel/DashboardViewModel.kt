package com.app.expensetracker.feature.expense.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.core.utils.generateMonths
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.usecase.GetExpensesByMonthUseCase
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.domain.usecase.ObserveMonthlyBudgetUseCase
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.domain.model.DashboardAggregate
import com.app.expensetracker.feature.expense.domain.usecase.GetRecentExpenseByMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getExpensesByMonth: GetExpensesByMonthUseCase,
    private val observeMonthlyBudget: ObserveMonthlyBudgetUseCase,
    private val getRecentExpenses: GetRecentExpenseByMonthUseCase,

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
        observeDashboardData()
        observeRecentExpenses()
    }

    fun onMonthSelected(month: YearMonthUiModel) {
        _selectedMonth.value = month
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
                emitEffect(ExpenseUiEffect.NavigateToExpenseDetail(event.expense))
            }

            is ExpenseUiEvent.OnMonthSelected -> {
                if (event.month != uiState.value.selectedMonth) {
                    _uiState.update { it.copy(selectedMonth = event.month,isLoading = true) }
                }
               // observeExpenses()

            }

            is ExpenseUiEvent.OnCategoryClicked -> {
                emitEffect(
                    ExpenseUiEffect.NavigateToCategory(event.category)
                )
            }

            ExpenseUiEvent.OnViewAllCategoriesClicked -> {
                emitEffect(
                    ExpenseUiEffect.NavigateToAllCategories
                )
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeDashboardData() {
        _selectedMonth.flatMapLatest { month ->

                combine(
                    getExpensesByMonth(
                        year = month.year,
                        month = month.month
                    ),
                    observeMonthlyBudget(
                        year = month.year,
                        month = month.month
                    )
                ) { expenses, budget ->

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
                        remainingBudget = remainingBudget
                    )
                }
            }
            .onEach { aggregate ->
                _uiState.update {
                    it.copy(
                        expenses = aggregate.expenses,
                        totalAmount = aggregate.totalAmount,
                        monthlyBudget = aggregate?.monthlyBudget ?: 0.0,
                        remainingBudget = aggregate.remainingBudget,
                        topCategories = aggregate.topCategories,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeRecentExpenses() {
        getRecentExpenses()
            .onEach { recent ->
                _uiState.update {
                    it.copy(recentExpenses = recent)
                }
            }
            .launchIn(viewModelScope)
    }


    /*

        @OptIn(ExperimentalCoroutinesApi::class)
        private fun observeExpenses() {
            _selectedMonth
                .flatMapLatest { month ->
                    getExpensesByMonth(
                        year = month.year,
                        month = month.month
                    )
                }
                .onEach { expenses ->
                    val total = expenses.sumOf { it.amount }
                    val topCategories =
                        expenses
                            .groupBy { it.category }
                            .map { (category, list) ->
                                CategorySummaryUiModel(
                                    category = category,
                                    spentAmount = list.sumOf { it.amount }
                                )
                            }
                            .sortedByDescending { it.spentAmount }
                            .take(3)

                    _uiState.update {
                        it.copy(
                            expenses = expenses,
                            totalAmount = total,
                            isLoading = false,
                            topCategories = topCategories
                        )
                    }
                }
                .launchIn(viewModelScope)
        }

        private fun observeBudget() {
            viewModelScope.launch {
                observeMonthlyBudget(
                    year = uiState.value.selectedMonth.year,
                    month = uiState.value.selectedMonth.month
                ).collect { budget ->

                    val remaining =
                        (budget?.monthlyBudget ?: 0.0) - uiState.value.totalAmount

                    _uiState.update {
                        it.copy(
                            monthlyBudget = budget?.monthlyBudget ?: 0.0,
                            remainingBudget = remaining.coerceAtLeast(0.0)
                        )
                    }
                }
            }
        }
    */


    private fun emitEffect(effect: ExpenseUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

}




