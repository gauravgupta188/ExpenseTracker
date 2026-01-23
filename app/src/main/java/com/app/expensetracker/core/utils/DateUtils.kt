package com.app.expensetracker.core.utils

import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
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
                )
            )
        }
    }
    return months
}


fun formatDateTime(
    dateTime: LocalDateTime,
    locale: Locale = Locale.getDefault()
): String {

    val today = LocalDate.now()
    val date = dateTime.toLocalDate()

    val timeFormatter =
        DateTimeFormatter.ofPattern("hh:mm a", locale)

    val dayMonthFormatter =
        DateTimeFormatter.ofPattern("dd MMM", locale)

    val fullDateFormatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy", locale)

    return when {
        // Today
        date.isEqual(today) ->
            "Today · ${dateTime.format(timeFormatter)}"

        // Yesterday
        date.isEqual(today.minusDays(1)) ->
            "Yesterday · ${dateTime.format(timeFormatter)}"

        // Within last 7 days
        ChronoUnit.DAYS.between(date, today) in 2..6 ->
            "${ChronoUnit.DAYS.between(date, today)} days ago · ${
                dateTime.format(timeFormatter)
            }"

        // Same year
        date.year == today.year ->
            "${date.format(dayMonthFormatter)} · ${
                dateTime.format(timeFormatter)
            }"

        // Older
        else ->
            "${date.format(fullDateFormatter)} · ${
                dateTime.format(timeFormatter)
            }"
    }
}
