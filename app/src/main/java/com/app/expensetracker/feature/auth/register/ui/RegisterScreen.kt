package com.app.expensetracker.feature.auth.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppOutlinedTextField
import com.app.expensetracker.feature.auth.login.state.LoginUiEvent
import com.app.expensetracker.feature.auth.login.state.LoginUiState
import com.app.expensetracker.feature.auth.register.state.RegisterUiEvent
import com.app.expensetracker.feature.auth.register.state.RegisterUiState
import com.app.expensetracker.ui.theme.BrandBlack
import com.app.expensetracker.ui.theme.BrandOrange
import com.app.expensetracker.ui.theme.BrandViolet


@Composable
fun RegisterScreen(
    uiState: RegisterUiState, onEvent: (RegisterUiEvent) -> Unit,onBackClick: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(BrandBlack, Color(0xFF1A1A1A))))
    ) {
        /* -------------------- Header -------------------- */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .statusBarsPadding()
                .background(
                    Brush.verticalGradient(
                        listOf(BrandBlack, Color(0xFF1A1A1A))
                    )
                )
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
                text = "Create Account",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start tracking your expenses with precision.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        /* -------------------- Form -------------------- */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(24.dp)
        ) {

            AppOutlinedTextField(
                value = uiState.fullName,
                onValueChange = { onEvent(RegisterUiEvent.OnFullNameChanged(it)) },
                label = "Full Name",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = uiState.fullNameError != null,
                supportingText = uiState.fullNameError,
                hintText = "e.g John Doe"

            )

            AppOutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(RegisterUiEvent.OnEmailChanged(it)) },
                label = "Email Address",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.emailError != null,
                supportingText = uiState.emailError,
                hintText = "name@example.com"

            )


            AppOutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(RegisterUiEvent.OnPasswordChanged(it)) },
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
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if(passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                })

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.isTermsAccepted,
                    onCheckedChange = {
                        onEvent(RegisterUiEvent.OnTermsChecked(it))
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = buildAnnotatedString {
                        append("I agree to the ")
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                                color = BrandViolet,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Terms of Service")
                        }
                        append(" and ")
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                                color = BrandViolet,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Privacy Policy")
                        }
                    },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onEvent(RegisterUiEvent.OnSignUpClicked) },
                enabled = uiState.isTermsAccepted && !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandOrange
                )
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text("Already have an account? ")
                TextButton(onClick = onBackClick) {
                    Text(
                        text = "Log in",
                        color = BrandViolet,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }
}