package com.app.expensetracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.expensetracker.feature.auth.login.ui.LoginScreen
import com.app.expensetracker.feature.auth.login.viewmodel.LoginViewModel
import com.app.expensetracker.feature.auth.register.ui.RegisterScreen
import com.app.expensetracker.feature.auth.register.viewmodel.RegisterViewModel
import com.app.expensetracker.feature.auth.resetpassword.ui.ResetPasswordScreen
import com.app.expensetracker.feature.auth.resetpassword.viewmodel.ResetPasswordViewModel
import com.app.expensetracker.feature.expense.addexpense.viewmodel.AddExpenseViewModel
import com.app.expensetracker.feature.expense.addexpense.ui.AddExpenseScreen

import com.app.expensetracker.feature.expense.dashboard.ui.DashboardScreen
import com.app.expensetracker.feature.expense.dashboard.viewmodel.DashboardViewModel
import com.app.expensetracker.feature.expense.summary.ui.MonthlySummaryScreen
import com.app.expensetracker.feature.expense.summary.viewmodel.MonthlySummaryViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) {
            Routes.MainRoot.route
        } else {
            Routes.AuthRoot.route
        }
    ) {
        // -------- AUTH --------
        navigation(
            startDestination = Routes.Login.route,
            route = Routes.AuthRoot.route,
        ) {
            composable(
                route = Routes.Login.route,
                enterTransition = { fadeInFast },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { fadeOutFast }
            ) {
                val viewModel: LoginViewModel = hiltViewModel()
                val context = LocalContext.current

                val credentialManager = remember {
                    CredentialManager.create(context)
                }


                LoginScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    uiEffect = viewModel.uiEffect,
                    onSignupClick = {
                        navController.navigate(Routes.Register.route)
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.AuthRoot.route) { inclusive = true }
                        }
                    },
                    onResetPasswordClick = {
                        navController.navigate(Routes.ResetPassword.route)
                    },
                    credentialManager = credentialManager,
                    onTokenReceived = viewModel::handleGoogleIdToken,
                    onGoogleError = viewModel::onGoogleSignInError
                )
            }

            composable(
                route = Routes.Register.route,

                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                val viewModel: RegisterViewModel = hiltViewModel()
                LaunchedEffect(Unit) {
                    viewModel.navigationEffect.collect {
                        navController.navigate(Routes.MainRoot.route) {
                            popUpTo(Routes.AuthRoot.route) { inclusive = true }
                        }
                    }
                }
                RegisterScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                Routes.ResetPassword.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }) {
                val viewModel: ResetPasswordViewModel = hiltViewModel()

                ResetPasswordScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    onBackClick = {
                        navController.popBackStack()
                    },

                    ) { }
            }
        }
        // -------- MAIN --------
        navigation(
            startDestination = Routes.AddExpense.route,
            route = Routes.MainRoot.route,
        ) {
            composable(Routes.Home.route) {
                val viewModel: DashboardViewModel = hiltViewModel()
                DashboardScreen(
                    state = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    onAddExpenseClick = {
                        navController.navigate(Routes.AddExpense.route)
                    },
                    onViewAllClick = {
                        navController.navigate(Routes.ExpenseSummary.route)
                    }


                )
            }

            composable(Routes.AddExpense.route) {
                val viewModel: AddExpenseViewModel = hiltViewModel()
                AddExpenseScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    uiEffect = viewModel.uiEffect,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.ExpenseSummary.route) {
                val viewModel : MonthlySummaryViewModel = hiltViewModel()
                MonthlySummaryScreen(
                    state = viewModel.uiState.collectAsState().value,
                    onBackClick = { navController.popBackStack() },
                    onMonthSelectorClick = { },
                    onAddExpenseClick = {},
                    onViewAllCategoriesClick = {},
                )
            }


        }

        composable(Routes.Profile.route) {
            /*  ProfileScreen(
                  viewModel = hiltViewModel(),
                  onBack = { navController.popBackStack() }
              )*/
        }

        composable(Routes.Settings.route) {
            /*  SettingsScreen(
                  viewModel = hiltViewModel(),
                  onBack = { navController.popBackStack() }
              )*/
        }
    }
}