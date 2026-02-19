package com.app.expensetracker.feature.settings.domain.model

object CurrencyProvider {

    val supportedCurrencies = listOf(
        CurrencyItem("USD", "US Dollar", "$"),
        CurrencyItem("INR", "Indian Rupee", "₹"),
        CurrencyItem("EUR", "Euro", "€"),
        CurrencyItem("GBP", "British Pound", "£")
    )

    fun getCurrencyByCode(code: String): CurrencyItem {
        return supportedCurrencies.find { it.code == code }
            ?: supportedCurrencies.first()
    }
}
