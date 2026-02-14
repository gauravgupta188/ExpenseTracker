package com.app.expensetracker.core.utils

import java.text.DecimalFormat

fun formattedAmount(amount: Double): String =
    "%,.2f".format(amount)

fun getDefaultCurrency() : String = "₹"

fun formattedCurrencyWithAmount(amount: Double): String =
    "${getDefaultCurrency()}${amount.formatAmount()}"

