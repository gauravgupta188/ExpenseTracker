package com.app.expensetracker.feature.auth.login.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import com.app.expensetracker.R
import com.app.expensetracker.core.components.AppButton
import com.app.expensetracker.core.components.AppCircularIcon
import com.app.expensetracker.core.components.AppLoader
import com.app.expensetracker.core.components.AppOutlinedTextField
import com.app.expensetracker.feature.auth.login.launchGoogleSignIn
import com.app.expensetracker.feature.auth.login.state.LoginUiEffect
import com.app.expensetracker.feature.auth.login.state.LoginUiEvent
import com.app.expensetracker.feature.auth.login.state.LoginUiState
import com.app.expensetracker.ui.theme.BrandBlack
import com.app.expensetracker.ui.theme.BrandOrange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.sin


@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    uiEffect: Flow<LoginUiEffect>,
    onSignupClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    credentialManager: CredentialManager,
    onTokenReceived: (String) -> Unit,
    onGoogleError: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember {
        credentialManager
    }





    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is LoginUiEffect.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(effect.message)
                }

                LoginUiEffect.NavigateToHome -> {
                    onNavigateToHome()
                }

                LoginUiEffect.LaunchGoogleSignIn -> {
                    coroutineScope.launch {
                        launchGoogleSignIn(
                            context = context,
                            credentialManager = credentialManager,
                            onTokenReceived = onTokenReceived,
                            onError = onGoogleError
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(BrandBlack, Color(0xFF1A1A1A))))
    ) {

        /* -------------------- Header -------------------- */
        Header()

        /* -------------------- Body -------------------- */
        LoginContent(
            uiState = uiState,
            onEvent = onEvent,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            onResetPasswordClick = onResetPasswordClick,
            onSignupClick = onSignupClick
        )

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )

        /* -------------------- App Loader -------------------- */
        if (uiState.isLoading) {
            AppLoader()
        }
    }

}

@Composable
private fun BoxScope.LoginContent(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .background(
                color = MaterialTheme.colorScheme.background,
                // shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(24.dp)
    ) {


        Spacer(modifier = Modifier.height(16.dp))

        AppOutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) },
            label = "Email Address",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = uiState.emailError != null,
            supportingText = uiState.emailError,
            hintText = "name@example.com"

        )


        AppOutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
            label = "Password",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = uiState.passwordError != null,
            supportingText = uiState.passwordError,
            hintText = "Enter Your Password",
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            })

        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { onResetPasswordClick() },
                // Align the button to the start (left) of the Box
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    "Forgot Password?", style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        AppButton(
            onClick = { onEvent(LoginUiEvent.OnLoginClicked) },
            enabled = !uiState.isLoading,
            text = stringResource(R.string.sign_in)

        )

        Spacer(modifier = Modifier.height(24.dp))
        /* -------------------- Social Media  -------------------- */

        SocialMediaSection(onEvent = onEvent)

        /* -------------------- SignUp Section  -------------------- */

        SignUpSection(onSignupClick = onSignupClick)
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .statusBarsPadding(),


        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        AppCircularIcon(Icons.Default.AccountBalanceWallet , 72.dp)
/*
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            *//* Replace with Image if logo drawable exists *//*
            Icon(
                imageVector = Icons.Default.Visibility,
                contentDescription = null,
                tint = BrandOrange,
                modifier = Modifier.size(32.dp)
            )
        }*/

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.welcome_back_to_expense_tracker),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SocialMediaSection( onEvent: (LoginUiEvent) -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.width(2.dp))
            Text(
                text = stringResource(R.string.or_continue_with),

                color = Color.Gray
            )
            Spacer(Modifier.width(2.dp))
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = MaterialTheme.colorScheme.outline
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedButton(
                onClick = {  onEvent(LoginUiEvent.OnGoogleLoginClicked) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = null,

                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text("Google")
                }
            }

            /*OutlinedButton(
                onClick = { *//* Facebook *//* },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_facebook_logo),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text("Facebook", color = BrandBlack)
                }
            }*/
        }

        Spacer(modifier = Modifier.height(24.dp))
    }


}

@Composable
fun  SignUpSection(onSignupClick: () -> Unit,){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Don't have an account? ", color = Color.Gray)
        TextButton(onClick = { onSignupClick() }, contentPadding = PaddingValues(0.dp)) {
            Text(
                text = "Sign Up",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


