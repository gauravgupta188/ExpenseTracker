package com.app.expensetracker.feature.expense.summary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.core.currency.CurrencyManager
import com.app.expensetracker.core.utils.AppLogger
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.SummaryAggregate
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.usecase.GetExpensesByMonthUseCase
import com.app.expensetracker.feature.expense.domain.usecase.ObserveMonthlyBudgetUseCase
import com.app.expensetracker.feature.expense.domain.usecase.SaveCategoryBudgetUseCase
import com.app.expensetracker.feature.expense.domain.usecase.SaveMonthlyBudgetUseCase
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEffect
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEvent
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlySummaryViewModel @Inject constructor(
    private val getExpenseByMonth: GetExpensesByMonthUseCase,
    private val observeMonthlyBudget: ObserveMonthlyBudgetUseCase,
    private val saveMonthlyBudget: SaveMonthlyBudgetUseCase,
    private val saveCategoryBudget: SaveCategoryBudgetUseCase,
    private val currencyManager: CurrencyManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MonthlySummaryUiState(
            selectedMonth = YearMonthUiModel.current(),
            isLoading = true
        )
    )
    val uiState: StateFlow<MonthlySummaryUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<MonthlySummaryUiEffect>()
    private val _selectedMonth =
        MutableStateFlow(YearMonthUiModel.current())

    init {
        observeCurrency()
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
            }

            is MonthlySummaryUiEvent.OnSaveBudget -> {

                saveMonthlyBudget(event.amount)
                _uiState.update {
                    it.copy(
                        monthlyBudget = event.amount,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }

            MonthlySummaryUiEvent.BudgetEditClicked -> {

                _uiState.update {
                    it.copy(showMonthlyBudgetSheet = true)
                }
            }

            MonthlySummaryUiEvent.CloseBudgetSheet -> {
                _uiState.update {
                    it.copy(showMonthlyBudgetSheet = false)
                }
            }

            is MonthlySummaryUiEvent.OnCategoryClicked -> {
                _uiState.update {
                    it.copy(
                        editingCategory = event.category,
                        showCategoryBudgetSheet = true
                    )
                }
            }

            is MonthlySummaryUiEvent.OnSaveCategoryBudget -> {
                saveCategoryBudget(
                    category = _uiState.value.editingCategory!!.category,
                    amount = event.amount,
                )


            }

            MonthlySummaryUiEvent.OnDismissBottomSheet -> {
                _uiState.update {
                    it.copy(
                        editingCategory = null,
                        showCategoryBudgetSheet = false
                    )
                }
            }

        }
    }

    private fun saveCategoryBudget(
        category: ExpenseCategory,
        amount: Double
    ) {
        viewModelScope.launch {
            try {
                saveCategoryBudget(
                    year = uiState.value.selectedMonth.year,
                    month = uiState.value.selectedMonth.month,
                    category = category,
                    amount = amount
                )

                _uiState.update {
                    it.copy(
                        editingCategory = null,
                        showCategoryBudgetSheet = false
                    )
                }
            } catch (e: Exception) {
                emitEffect(
                    MonthlySummaryUiEffect.ShowError(
                        "Failed to save budget"
                    )
                )
            }
        }
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

    private fun saveMonthlyBudget(amount: Double) {
        val state = _uiState.value

        if (amount <= 0) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                saveMonthlyBudget(
                    year = state.selectedMonth.year,
                    month = state.selectedMonth.month,
                    amount = amount
                )
                //Close MonthlyBottomSheet after save
                _uiState.update {
                    it.copy(showMonthlyBudgetSheet = false)
                }

            } catch (e: Exception) {

            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeSummaryData(monthFlow: StateFlow<YearMonthUiModel>) {
        monthFlow.flatMapLatest { month ->
            AppLogger.d("TAG", "observeSummaryData: $month")

            combine(
                getExpenseByMonth(
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
                var remainingBudget = monthlyBudget - totalAmount

                val categoryBudgets =
                    budget?.categoryBudgets.orEmpty()

                val categories =
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


                SummaryAggregate(
                    // expenses = expenses,
                    categories = categories,
                    monthlyBudget = monthlyBudget,
                    totalAmount = totalAmount,
                    remainingBudget = remainingBudget,
                    month = month
                )
            }
        }
            .onEach { aggregate ->
                _uiState.update {

                    it.copy(
                        isLoading = false,
                        categories = aggregate.categories,
                        monthlyBudget = aggregate.monthlyBudget ?: 0.0,
                        remainingAmount = aggregate.remainingBudget,
                        totalSpent = aggregate.totalAmount,
                        selectedMonth = aggregate.month,
                        errorMessage = null
                    )
                    /*  it.copy(
                          expenses = aggregate.expenses,
                          categories = aggregate.categories,
                          monthlyBudget = aggregate.monthlyBudget
                      )*/
                }
            }
            .launchIn(viewModelScope)
    }

    /*  private fun loadMonth(month: YearMonthUiModel) {
          summaryJob?.cancel()

          summaryJob = viewModelScope.launch {
              getMonthlySummary(
                  year = month.year,
                  month = month.month
              ).collect { categories ->

                  val totalSpent = categories.sumOf { it.spentAmount }
                  val remainingAmount = _uiState.value.monthlyBudget - totalSpent


                  _uiState.update {
                      it.copy(
                          isLoading = false,
                          categories = categories,
                          totalSpent = totalSpent,
                          remainingAmount = remainingAmount,
                          errorMessage = null
                      )
                  }
              }
          }
      }

      private fun observeBudget() {
          viewModelScope.launch {
              observeMonthlyBudget(
                  year = uiState.value.selectedMonth.year,
                  month = uiState.value.selectedMonth.month
              ).collect { budget ->

                  _uiState.update {
                      it.copy(
                        //  budgetAmount = budget?.monthlyBudget ?? 0,
                          //categoryBudgets = budget?.categoryBudgets.orEmpty()
                      )
                  }
              }
          }
      }
  */
    private fun emitEffect(effect: MonthlySummaryUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}


