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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.expensetracker.R
import com.app.expensetracker.core.components.AppButton
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

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(BrandBlack, Color(0xFF1A1A1A))))

        ) {

            /* -------------------- Top Bar -------------------- */

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
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
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.reset_password),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.reset_your_password_with_ease),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }


            /* -------------------- Title -------------------- */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        //  shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.forgot_your_password),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.enter_the_email_address_associated_with_your_account_and_we_ll_send_you_a_link_to_reset_your_password),
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                /* -------------------- Email Field -------------------- */
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    AppOutlinedTextField(
                        value = uiState.email,
                        label = stringResource(R.string.email_address),
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
                Box(modifier = Modifier.padding(24.dp)) {
                    AppButton(
                        onClick = { onEvent(ResetPasswordUiEvent.OnSendResetLinkClicked) },
                        enabled = !uiState.isLoading,
                        text = stringResource(R.string.send_reset_link)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                /* -------------------- Try Again -------------------- */
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.didn_t_receive_the_email),
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = { onEvent(ResetPasswordUiEvent.OnTryAgainClicked) }
                    ) {
                        Text(
                            text = stringResource(R.string.try_again),
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                /* -------------------- Back to Login -------------------- */
                TextButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(R.string.back_to_login),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }
    }
}
