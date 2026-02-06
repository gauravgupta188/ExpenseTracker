package com.app.expensetracker.feature.expense.domain.model

import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class YearMonthUiModel(
    val year: Int,
    val month: Int // 1..12
) {

    val label: String
        get() = Month.of(month)
            .getDisplayName(TextStyle.SHORT, Locale.getDefault())
            .uppercase() + " $year"

    val rangeLabel: String
        get() = "$label • 1–${lengthOfMonth()}"

    fun lengthOfMonth(): Int =
        YearMonth.of(year, month).lengthOfMonth()



    fun previous(): YearMonthUiModel {
        return if (month == 1) {
            YearMonthUiModel(
                year = year - 1,
                month = 12
            )
        } else {
            YearMonthUiModel(
                year = year,
                month = month - 1
            )
        }
    }

    fun next(): YearMonthUiModel {
        return if (month == 12) {
            YearMonthUiModel(
                year = year + 1,
                month = 1
            )
        } else {
            YearMonthUiModel(
                year = year,
                month = month + 1
            )
        }
    }

    fun isFuture(): Boolean {
        val now = YearMonth.now()
        return year > now.year ||
                (year == now.year && month > now.monthValue)
    }


    private fun monthName(): String {
        return Month.of(month)
            .getDisplayName(
                TextStyle.SHORT,
                Locale.getDefault()
            )
    }

    companion object {
        fun current(): YearMonthUiModel {
            val now = YearMonth.now()
            return YearMonthUiModel(
                year = now.year,
                month = now.monthValue
            )
        }
    }
}

