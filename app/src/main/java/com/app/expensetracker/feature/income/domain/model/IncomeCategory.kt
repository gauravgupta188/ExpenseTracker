package com.app.expensetracker.feature.income.domain.model

enum class IncomeCategory(val value: String) {
    SALARY("SALARY"),
    FREELANCE("FREELANCE"),
    BUSINESS("BUSINESS"),
    INVESTMENT("INVESTMENT"),
    RENTAL("RENTAL"),
    GIFT("GIFT"),
    OTHER("OTHER");

    companion object {
        fun fromValue(value: String): IncomeCategory =
            entries.firstOrNull { it.value.equals(value, ignoreCase = true) } ?: OTHER
    }
}
