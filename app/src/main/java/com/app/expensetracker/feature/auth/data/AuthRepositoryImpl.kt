package com.app.expensetracker.feature.auth.data

import com.app.expensetracker.feature.auth.domain.model.AuthUser
import com.app.expensetracker.feature.auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): AuthUser {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("User creation failed")

        return AuthUser(
            uid = user.uid,
            email = user.email
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): AuthUser {
        val result = firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("Login failed")

        return AuthUser(
            uid = user.uid,
            email = user.email
        )
    }

    override fun getCurrentUser(): AuthUser? {
        return firebaseAuth.currentUser?.let {
            AuthUser(it.uid, it.email)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override fun isUserLoggedIn(): Boolean {
        return  firebaseAuth.currentUser != null
    }
}
