package com.app.expensetracker.feature.auth.domain.repository

import com.app.expensetracker.feature.auth.domain.model.AuthUser

interface AuthRepository {
    suspend fun register(email: String, password: String): AuthUser
    suspend fun login(email: String, password: String): AuthUser
    fun getCurrentUser(): AuthUser?
    suspend fun logout()

    suspend fun loginWithGoogle(idToken: String)
    fun isUserLoggedIn(): Boolean
}