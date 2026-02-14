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

