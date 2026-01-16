package com.app.expensetracker.core.utils

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

fun generateMonths(
    startYear: Int,
    endYear: Int
): List<YearMonthUiModel> {
    require(startYear <= endYear) {
        "startYear must be <= endYear"
    }

    val months = mutableListOf<YearMonthUiModel>()

    for (year in startYear..endYear) {
        for (month in 1..12) {
            val yearMonth = YearMonth.of(year, month)

            months.add(
                YearMonthUiModel(
                    year = yearMonth.year,
                    month = yearMonth.monthValue,
                    label = yearMonth.month.getDisplayName(
                        TextStyle.SHORT,
                        Locale.ENGLISH
                    )
                )
            )
        }
    }
    return months
}
