package com.app.expensetracker.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDateTime.toDisplayDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern(
        "MMM dd, yyyy • hh:mm a",
        Locale.ENGLISH
    )
    return format(formatter)
}