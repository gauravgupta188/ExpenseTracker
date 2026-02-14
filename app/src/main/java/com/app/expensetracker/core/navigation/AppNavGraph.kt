package com.app.expensetracker.core.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

import androidx.credentials.CredentialManager
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.expensetracker.core.ui.theme.SplashScreen
import com.app.expensetracker.feature.auth.login.ui.LoginScreen
import com.app.expensetracker.feature.auth.login.viewmodel.LoginViewModel
import com.app.expensetracker.feature.auth.register.ui.RegisterScreen
import com.app.expensetracker.feature.auth.register.viewmodel.RegisterViewModel
import com.app.expensetracker.feature.auth.resetpassword.ui.ResetPasswordScreen
import com.app.expensetracker.feature.auth.resetpassword.viewmodel.ResetPasswordViewModel
import com.app.expensetracker.feature.expense.addexpense.ui.AddExpenseScreen
import com.app.expensetracker.feature.expense.addexpense.viewmodel.AddExpenseViewModel
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiEffect
import com.app.expensetracker.feature.expense.categorydetail.ui.CategoryDetailScreen
import com.app.expensetracker.feature.expense.categorydetail.viewmodel.CategoryDetailViewModel
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect
import com.app.expensetracker.feature.expense.dashboard.ui.DashboardScreen
import com.app.expensetracker.feature.expense.dashboard.viewmodel.DashboardViewModel
import com.app.expensetracker.feature.expense.expensedetails.state.ExpenseDetailUiEffect
import com.app.expensetracker.feature.expense.expensedetails.ui.ExpenseDetailScreen
import com.app.expensetracker.feature.expense.expensedetails.viewmodel.ExpenseDetailViewModel
import com.app.expensetracker.feature.expense.monthlyexpense.state.MonthlyExpensesUiEffect
import com.app.expensetracker.feature.expense.monthlyexpense.state.MonthlyExpensesUiEvent
import com.app.expensetracker.feature.expense.monthlyexpense.ui.MonthlyExpensesScreen
import com.app.expensetracker.feature.expense.monthlyexpense.viewmodel.MonthlyExpensesViewModel
import com.app.expensetracker.feature.expense.summary.ui.MonthlySummaryScreen
import com.app.expensetracker.feature.expense.summary.viewmodel.MonthlySummaryViewModel
import com.app.expensetracker.feature.expense.viewmodel.AppDateViewModel
import com.app.expensetracker.feature.settings.state.SettingsUiEffect
import com.app.expensetracker.feature.settings.ui.SettingsScreen
import com.app.expensetracker.feature.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    appDateViewModel: AppDateViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SplashRoot.route
    ) {
        navigation(startDestination = Routes.Splash.route, route = Routes.SplashRoot.route) {
            composable(Routes.Splash.route) {
                SplashScreen()

                LaunchedEffect(Unit) {
                    delay(1500) // branding delay
                    navController.navigate(
                        if (isLoggedIn) {
                            Routes.Home.route
                        } else {
                            Routes.Login.route
                        }
                    ) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            }
        }

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
            startDestination = Routes.Home.route,
            route = Routes.MainRoot.route,
        ) {
            composable(
                Routes.Home.route, enterTransition = { fadeInFast },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { fadeOutFast }) {
                val appDateState by appDateViewModel.uiState.collectAsState()
                val selectedMonthFlow = appDateViewModel.selectedMonth

                val viewModel: DashboardViewModel = hiltViewModel()
                LaunchedEffect(Unit) {
                    viewModel.observeDashboardData(selectedMonthFlow)
                }


                DashboardScreen(
                    state = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    onAddExpenseClick = {
                        navController.navigate(Routes.AddExpense.route)
                    },
                    onViewAllClick = {
                        navController.navigate(Routes.MonthlyExpenses.route)
                    },

                    onSettingsClick = {
                        navController.navigate(Routes.Settings.route)
                    },


                    appDateUiState = appDateState,
                    onDateEvent = appDateViewModel::onEvent,
                )

                LaunchedEffect(Unit) {
                    viewModel.uiEffect.collect { effect ->
                        when (effect) {
                            ExpenseUiEffect.NavigateToMonthlyExpenses ->
                                navController.navigate(Routes.MonthlyExpenses.route)

                            ExpenseUiEffect.NavigateToAllCategories -> {
                                val month = appDateState.selectedMonth
                                Log.d("MOnth", month.toString())
                                val route = Routes.MonthlySummary.routeWithMonth(
                                    year = month.year,
                                    month = month.month
                                )
                                Log.d("Route", route)
                                navController.navigate(
                                    Routes.MonthlySummary.routeWithMonth(
                                        year = month.year,
                                        month = month.month
                                    )
                                )
                            }

                            is ExpenseUiEffect.NavigateToCategory -> {
                                val month = appDateState.selectedMonth
                                navController.navigate(
                                    Routes.CategoryDetail.createRoute(
                                        category = effect.category,
                                        year = month.year,
                                        month = month.month
                                    )
                                )
                            }

                            is ExpenseUiEffect.NavigateToExpenseDetail -> {
                                navController.navigate(
                                    Routes.ExpenseDetail.createRoute(
                                        effect.expense.id

                                    )
                                )
                            }
                        }
                    }
                }
            }

            composable(
                Routes.AddExpense.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }) {
                val viewModel: AddExpenseViewModel = hiltViewModel()
                AddExpenseScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    uiEffect = viewModel.uiEffect,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                Routes.MonthlySummary.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }) {
                val viewModel: MonthlySummaryViewModel = hiltViewModel()

                LaunchedEffect(Unit) {
                    viewModel.observeSummaryData(appDateViewModel.selectedMonth)
                }

                MonthlySummaryScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    onBackClick = { navController.popBackStack() },
                    onMonthSelectorClick = { },
                    onAddExpenseClick = {},
                    onViewAllCategoriesClick = {},
                    onCategoryClick = { category ->
                        val month = viewModel.uiState.value.selectedMonth
                        navController.navigate(
                            Routes.CategoryDetail.createRoute(
                                category = category.category,
                                year = month.year,
                                month = month.month
                            )
                        )
                    }
                )
            }
            composable(
                Routes.MonthlyExpenses.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }) {
                val viewModel: MonthlyExpensesViewModel = hiltViewModel()

                val selectedMonthFlow = appDateViewModel.selectedMonth

                LaunchedEffect(Unit) {
                    viewModel.observeExpenses(selectedMonthFlow)
                }
                MonthlyExpensesScreen(
                    state = viewModel.uiState.collectAsState().value,
                    onBackClick = {
                        viewModel.onEvent(MonthlyExpensesUiEvent.OnBackClicked)
                    },
                    onExpenseClick = { expense ->
                        viewModel.onEvent(
                            MonthlyExpensesUiEvent.OnExpenseClicked(expense.id)
                        )
                    },
                    onAddExpenseClick = {
                        viewModel.onEvent(
                            MonthlyExpensesUiEvent.OnAddExpenseClicked
                        )
                    }
                )

                LaunchedEffect(Unit) {
                    viewModel.uiEffect.collect { effect ->
                        when (effect) {
                            MonthlyExpensesUiEffect.NavigateBack ->
                                navController.popBackStack()

                            MonthlyExpensesUiEffect.NavigateToAddExpense ->
                                navController.navigate(Routes.AddExpense.route)

                            is MonthlyExpensesUiEffect.NavigateToExpenseDetail -> {}
                            /* navController.navigate(
                                 Routes.ExpenseDetail.createRoute(effect.expenseId)
                             )*/
                        }
                    }
                }
            }

            composable(
                route = Routes.CategoryDetail.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight },
                arguments = listOf(
                    navArgument("category") { type = NavType.StringType },
                    navArgument("year") { type = NavType.IntType },
                    navArgument("month") { type = NavType.IntType }
                )
            ) {
                val viewModel: CategoryDetailViewModel = hiltViewModel()

                CategoryDetailScreen(
                    state = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent
                )

                LaunchedEffect(Unit) {
                    viewModel.uiEffect.collect { effect ->
                        when (effect) {
                            CategoryDetailUiEffect.NavigateBack ->
                                navController.popBackStack()

                            CategoryDetailUiEffect.NavigateToAddExpense ->
                                navController.navigate(Routes.AddExpense.route)

                            CategoryDetailUiEffect.OpenFilter -> {
                                // future
                            }

                            is CategoryDetailUiEffect.NavigateToExpenseDetail -> {
                                navController.navigate(
                                    Routes.ExpenseDetail.createRoute(
                                        effect.expense.id

                                    )
                                )
                            }

                            is CategoryDetailUiEffect.ShowError -> {}
                        }
                    }
                }
            }

            composable(
                Routes.ExpenseDetail.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }) {
                val viewModel: ExpenseDetailViewModel = hiltViewModel()

                ExpenseDetailScreen(
                    state = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    uiEffect = viewModel.uiEffect,
                    onBackClick = { navController.popBackStack() },
                    onEditClick = {
                        viewModel.uiState.value.expense?.id?.let { expenseId ->
                            navController.navigate(
                                Routes.EditExpense.createRoute(expenseId = expenseId)
                            )
                        }
                    }
                )
            }

            composable(
                Routes.EditExpense.route, enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight },
            ) {
                val viewModel: AddExpenseViewModel = hiltViewModel()

                AddExpenseScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onEvent = viewModel::onEvent,
                    uiEffect = viewModel.uiEffect,
                    onBack = { navController.popBackStack() }
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
            val viewModel: SettingsViewModel = hiltViewModel()

            SettingsScreen(
                onEvent = viewModel::onEvent,
                uiState = viewModel.uiState.collectAsState().value,
                onBackClick = {
                    navController.popBackStack()
                }
            )

            LaunchedEffect(Unit) {
                viewModel.uiEffect.collect { effect ->
                    when (effect) {
                        SettingsUiEffect.NavigateBack ->
                            navController.popBackStack()

                        SettingsUiEffect.LogoutSuccess -> {
                            navController.navigate(Routes.AuthRoot.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }

                        SettingsUiEffect.NavigateToCurrency -> {}
                        SettingsUiEffect.NavigateToPasscode -> {}
                        SettingsUiEffect.NavigateToProfile -> {}
                        SettingsUiEffect.NavigateToSubscription -> {}
                        SettingsUiEffect.NavigateToSupport -> {}
                    }
                }
            }
        }
    }
}