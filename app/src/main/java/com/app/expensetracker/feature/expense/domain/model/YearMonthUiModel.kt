package com.app.expensetracker.feature.expense.domain.model

import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class YearMonthUiModel(
    val year: Int,
    val month: Int,
    val label: String
) {
    companion object {
        fun current(): YearMonthUiModel {
            val now = YearMonth.now()
            return YearMonthUiModel(
                year = now.year,
                month = now.monthValue,
                label = now.month.getDisplayName(
                    TextStyle.SHORT,
                    Locale.ENGLISH
                )
            )
        }
    }
}
