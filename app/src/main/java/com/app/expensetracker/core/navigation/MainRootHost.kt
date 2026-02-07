package com.app.expensetracker.core.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEffect
import com.app.expensetracker.feature.expense.dashboard.ui.DashboardScreen
import com.app.expensetracker.feature.expense.dashboard.viewmodel.DashboardViewModel
import com.app.expensetracker.feature.expense.summary.ui.MonthlySummaryScreen
import com.app.expensetracker.feature.expense.summary.viewmodel.MonthlySummaryViewModel
import com.app.expensetracker.feature.expense.viewmodel.AppDateViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
private fun MainRootHost(
    navController: NavHostController
) {
    // ✅ Safe: composable context
    val mainRootEntry = remember(navController) {
        navController.getBackStackEntry(Routes.MainRoot.route)
    }

    val appDateViewModel: AppDateViewModel =
        hiltViewModel(mainRootEntry)

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {

        composable(
            Routes.Home.route, enterTransition = { fadeInFast },
            exitTransition = { slideOutToLeft },
            popEnterTransition = { slideInFromLeft },
            popExitTransition = { fadeOutFast }) {


            /*  val viewModelStoreOwner =
                  LocalViewModelStoreOwner.current
                      ?: return@composable

              val appDateViewModel: AppDateViewModel =
                  hiltViewModel(viewModelStoreOwner)*/

            //  val appDateViewModel: AppDateViewModel = hiltViewModel()
            //val appDateViewModel: AppDateViewModel =
              //  hiltViewModel()
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
            Routes.MonthlySummary.route, enterTransition = { slideInFromRight },
            exitTransition = { slideOutToLeft },
            popEnterTransition = { slideInFromLeft },
            popExitTransition = { slideOutToRight }) { backStackEntry ->
            val viewModel: MonthlySummaryViewModel = hiltViewModel()
         //   val appDateViewModel: AppDateViewModel =
           //     hiltViewModel()
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
    }}
