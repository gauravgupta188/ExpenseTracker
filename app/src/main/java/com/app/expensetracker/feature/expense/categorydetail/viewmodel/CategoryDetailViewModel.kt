package com.app.expensetracker.feature.expense.categorydetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.core.currency.CurrencyManager
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiEffect
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiEvent
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiState
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect.NavigateToExpenseDetail
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.app.expensetracker.feature.expense.domain.usecase.SaveCategoryBudgetUseCase
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle,
    private val saveCategoryBudget: SaveCategoryBudgetUseCase,
    private val currencyManager: CurrencyManager
) : ViewModel() {

    private val category =
        ExpenseCategory.valueOf(savedStateHandle["category"]!!)

    private val year = savedStateHandle.get<Int>("year")!!
    private val month = savedStateHandle.get<Int>("month")!!

    private val _uiState = MutableStateFlow(
        CategoryDetailUiState(
            category = category,
            yearMonth = YearMonthUiModel(
                year, month,
            )
        )
    )
    val uiState: StateFlow<CategoryDetailUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<CategoryDetailUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        observeExpenses()
        observeBudget()
        observeCurrency()
    }

    private fun observeBudget() {
        viewModelScope.launch {
            repository.observeMonthlyBudget(year, month)
                .collect { budget ->
                    val budgetOfCategory = budget?.categoryBudgets[category] ?: 0.0
                    _uiState.update {
                        it.copy(
                            budgetAmount = budgetOfCategory
                        )
                    }
                }
        }
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            repository.getExpensesByMonth(year, month)
                .map { list -> list.filter { it.category == category } }
                .collect { expenses ->
                    _uiState.update {
                        it.copy(
                            expenses = expenses,
                            totalSpent = expenses.sumOf { e -> e.amount },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onEvent(event: CategoryDetailUiEvent) {
        when (event) {
            CategoryDetailUiEvent.OnBackClicked ->
                emitEffect(CategoryDetailUiEffect.NavigateBack)

            CategoryDetailUiEvent.OnAddExpenseClicked ->
                emitEffect(CategoryDetailUiEffect.NavigateToAddExpense)

            CategoryDetailUiEvent.OnFilterClicked ->
                emitEffect(CategoryDetailUiEffect.OpenFilter)

            is CategoryDetailUiEvent.ExpenseClicked -> {
                emitEffect(CategoryDetailUiEffect.NavigateToExpenseDetail(event.expense))
            }
            CategoryDetailUiEvent.OnViewAllExpensesClicked -> {

            }

            is CategoryDetailUiEvent.OnSaveCategoryBudget -> {
                saveCategoryBudget(
                    category = category,
                    amount = event.amount,
                )
            }
            CategoryDetailUiEvent.OnDismissBottomSheet -> {
                _uiState.update {
                    it.copy(showCategoryBudgetSheet = false)
                }
            }

            CategoryDetailUiEvent.OnCategoryClicked -> {
                _uiState.update {
                    it.copy(showCategoryBudgetSheet = true)
                }
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


    private fun saveCategoryBudget(
        category: ExpenseCategory,
        amount: Double
    ) {
        viewModelScope.launch {
            try {saveCategoryBudget(
                year = year,
                month = month,
                category = category,
                amount = amount
            )

                _uiState.update {
                    it.copy(
                        showCategoryBudgetSheet = false
                    )
                }
            }catch (e: Exception) {
                emitEffect(
                    CategoryDetailUiEffect.ShowError(
                        "Failed to save budget"
                    )
                )
            }
        }
    }

    private fun emitEffect(effect: CategoryDetailUiEffect) {
        viewModelScope.launch { _uiEffect.emit(effect) }
    }
}
