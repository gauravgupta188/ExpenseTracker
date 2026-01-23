package com.app.expensetracker.feature.auth.resetpassword.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppOutlinedTextField
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiEvent
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiState
import com.app.expensetracker.ui.theme.BrandBlack
import com.app.expensetracker.ui.theme.BrandOrange
import com.app.expensetracker.ui.theme.BrandViolet

@Composable
fun ResetPasswordScreen(
    uiState: ResetPasswordUiState,
    onEvent: (ResetPasswordUiEvent) -> Unit,
    onBackClick: () -> Unit,
    onBackToLoginClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {

        /* -------------------- Top Bar -------------------- */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(BrandBlack, Color(0xFF1A1A1A))
                    )
                )
                .statusBarsPadding()
                .padding(16.dp)
        ) {

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reset your password with ease.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
      /*  Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(BrandBlack)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Reset Password",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))*/

        /* -------------------- Icon -------------------- */
       /* Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    color = BrandViolet.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LockReset,
                contentDescription = null,
                tint = BrandViolet,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))*/

        /* -------------------- Title -------------------- */
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Forgot Your Password?",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Enter the email address associated with your account and we'll send you a link to reset your password.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* -------------------- Email Field -------------------- */
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            AppOutlinedTextField(
                value = uiState.email,
                label = "Email Address",
                hintText = "example@email.com",
                onValueChange = {
                    onEvent(ResetPasswordUiEvent.OnEmailChanged(it))
                },
                isError = uiState.emailError != null,
                supportingText = uiState.emailError
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        /* -------------------- CTA -------------------- */
        Button(
            onClick = { onEvent(ResetPasswordUiEvent.OnSendResetLinkClicked) },
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrandOrange
            )
        ) {
            Text(
                text = "Send Reset Link",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        /* -------------------- Try Again -------------------- */
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Didn't receive the email? ",
                color = Color.Gray
            )
            TextButton(
                onClick = { onEvent(ResetPasswordUiEvent.OnTryAgainClicked) }
            ) {
                Text(
                    text = "Try again",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        /* -------------------- Back to Login -------------------- */
        TextButton(
            onClick = onBackToLoginClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "← Back to Login",
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
        
    }
}
