package com.app.expensetracker.feature.auth.domain.repository

import android.net.Uri
import com.app.expensetracker.feature.auth.domain.model.AuthUser

interface AuthRepository {
    suspend fun register(email: String, password: String,displayName:String): AuthUser
    suspend fun login(email: String, password: String): AuthUser
    fun getCurrentUser(): AuthUser?
    suspend fun logout()

    suspend fun loginWithGoogle(idToken: String)
    suspend fun updateDisplayName(displayName: String)
    suspend fun updateProfilePhoto(photoUrl: String)
    suspend fun uploadProfileImage(photoUri: Uri): String
    fun isUserLoggedIn(): Boolean
}