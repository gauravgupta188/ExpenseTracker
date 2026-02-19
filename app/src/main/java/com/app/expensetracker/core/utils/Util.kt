package com.app.expensetracker.core.utils

import com.app.expensetracker.feature.settings.domain.model.CurrencyItem


fun formattedAmount(amount: Double): String =
    "%,.2f".format(amount)

fun getDefaultCurrency() : String = "₹"

fun formattedCurrencyWithAmount(amount: Double,currency: CurrencyItem): String =
    "${currency.symbol}${amount.formatAmount()}"



