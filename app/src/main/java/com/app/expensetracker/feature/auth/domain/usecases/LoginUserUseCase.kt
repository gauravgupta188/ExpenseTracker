package com.app.expensetracker.feature.auth.domain.usecases

import com.app.expensetracker.feature.auth.domain.repository.AuthRepository


class LoginUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.login(email, password)
}
