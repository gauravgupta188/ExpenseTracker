package com.app.expensetracker.feature.expense.domain.model

enum class ExpenseCategory(val value: String) {
    FOOD("FOOD"),
    TRANSPORT("TRANSPORT"),
    RENT("RENT"),
    SHOPPING("SHOPPING"),
    BILLS(value = "BILL"),
    OTHER("OTHER");

    companion object {
        fun fromValue(value: String): ExpenseCategory {
            return values().firstOrNull {
                it.value.equals(value, ignoreCase = true)
            } ?: OTHER
        }
    }
}
