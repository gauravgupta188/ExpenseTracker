package com.app.expensetracker.feature.auth.domain.usecases

import com.app.expensetracker.feature.auth.domain.repository.AuthRepository

class CheckUserLoggedInUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean =
        authRepository.isUserLoggedIn()
}
