package com.app.expensetracker.feature.auth.data

import android.net.Uri
import com.app.expensetracker.feature.auth.domain.model.AuthUser
import com.app.expensetracker.feature.auth.domain.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    private val storage = Firebase.storage


    override suspend fun register(
        email: String,
        password: String,
        displayName:String,
    ): AuthUser {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("User creation failed")

        // 🔥 Update Firebase profile
        val profileUpdates = userProfileChangeRequest {
            this.displayName = displayName
        }

        user.updateProfile(profileUpdates).await()

        // 🔥 IMPORTANT: reload user
        user.reload().await()

        return AuthUser(
            uid = user.uid,
            email = user.email,
            displayName = user.displayName
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
            email = user.email,
            displayName = user.displayName
        )
    }

    override fun getCurrentUser(): AuthUser? {
        return firebaseAuth.currentUser?.let {
            AuthUser(it.uid, it.email,it.displayName)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun updateDisplayName(name: String) {
        val user = firebaseAuth.currentUser ?: return

        val request = userProfileChangeRequest {
            displayName = name
        }

        user.updateProfile(request).await()
        user.reload().await()
    }

    override suspend fun updateProfilePhoto(photoUrl: String) {

        val user = firebaseAuth.currentUser ?: return

        val request = userProfileChangeRequest {
            this.photoUri = Uri.parse(photoUrl)
        }

        user.updateProfile(request).await()
        user.reload().await()
    }

    override suspend fun uploadProfileImage(uri: Uri): String {

        val user = firebaseAuth.currentUser
            ?: throw IllegalStateException("No user")

        val ref = storage.reference
            .child("profile_images/${user.uid}.jpg")

        ref.putFile(uri).await()

        return ref.downloadUrl.await().toString()
    }

    override fun isUserLoggedIn(): Boolean {
        return  firebaseAuth.currentUser != null
    }
}
