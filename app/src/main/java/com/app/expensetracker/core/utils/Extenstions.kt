package com.app.expensetracker.core.utils

import java.text.DecimalFormat

fun Double.formatAmount(): String {
    val df = DecimalFormat("#.##")
    df.isGroupingUsed = true
    return df.format(this)
}

fun String.defaultCurrency():String {
    return "${getDefaultCurrency()}$this"
}

fun String.initials(): String {
    if (isBlank()) return "U"

    val parts = trim().split("\\s+".toRegex())

    return when {
        parts.size == 1 ->
            parts.first().take(1).uppercase()

        else ->
            (parts[0].take(1) + parts[1].take(1)).uppercase()
    }
}

