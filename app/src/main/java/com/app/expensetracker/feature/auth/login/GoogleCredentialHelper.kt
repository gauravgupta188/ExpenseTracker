package com.app.expensetracker.feature.auth.login
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.app.expensetracker.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL

suspend fun launchGoogleSignIn(
    context: Context,
    credentialManager: CredentialManager,
    onTokenReceived: (String) -> Unit,
    onError: () -> Unit
) {
    try {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(
                context.getString(R.string.default_web_client_id)
            )
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(
            context = context,
            request = request
        )

        val credential = result.credential

        if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            onTokenReceived(googleCredential.idToken)
        } else {
            onError()
        }

    } catch (e: GetCredentialException) {
        onError()
    }
}
