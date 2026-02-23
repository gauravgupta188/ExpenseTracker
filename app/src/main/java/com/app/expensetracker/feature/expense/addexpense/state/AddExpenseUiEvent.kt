package com.app.expensetracker.feature.expense.addexpense.state

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

sealed interface AddExpenseUiEvent {

    data class AmountChanged(val value: Double) : AddExpenseUiEvent
    data class CategorySelected(val category: ExpenseCategory) : AddExpenseUiEvent
    data class NoteChanged(val value: String) : AddExpenseUiEvent

    object SaveClicked : AddExpenseUiEvent
    object DateClicked : AddExpenseUiEvent
    data class DateSelected(val date: LocalDate) : AddExpenseUiEvent

    data class TimeSelected(val time: LocalTime) : AddExpenseUiEvent

    object SeeAllCategoriesClicked : AddExpenseUiEvent
    object CloseCategorySheet : AddExpenseUiEvent

}

