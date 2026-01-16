package com.app.expensetracker.feature.auth.domain.usecases

import com.app.expensetracker.feature.auth.domain.repository.AuthRepository

class LoginWithGoogleUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) {
        repository.loginWithGoogle(idToken)
    }
}
