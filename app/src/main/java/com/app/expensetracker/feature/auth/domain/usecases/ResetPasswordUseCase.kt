package com.app.expensetracker.feature.auth.domain.usecases

import com.app.expensetracker.feature.auth.domain.repository.AuthRepository

class ResetPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String
    ) = repository.resetPassword(email)
}
