package com.app.expensetracker.core.utils

import android.util.Patterns

object Validation {

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Enter a valid email address"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 ->
                "Password must be at least 6 characters"
            else -> null
        }
    }
}
