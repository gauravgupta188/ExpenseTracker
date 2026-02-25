package com.app.expensetracker.feature.auth.domain.usecases

import com.app.expensetracker.feature.auth.domain.repository.AuthRepository

class RegisterUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        displayName: String
    ) = repository.register(email, password,displayName)
}
