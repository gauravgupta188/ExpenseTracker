package com.app.expensetracker.feature.expense.domain.model

import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class YearMonthUiModel(
    val year: Int,
    val month: Int,

) {

    val label: String
        get() = Month.of(month)
            .getDisplayName(TextStyle.SHORT, Locale.getDefault())
            .uppercase() + " $year"

    val rangeLabel: String
        get() = "$label • 1–${lengthOfMonth()}"

    fun lengthOfMonth(): Int =
        YearMonth.of(year, month).lengthOfMonth()
    companion object {
        fun current(): YearMonthUiModel {
            val now = YearMonth.now()
            return YearMonthUiModel(
                year = now.year,
                month = now.monthValue,
            )
        }
    }
}
