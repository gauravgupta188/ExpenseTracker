package com.app.expensetracker.core.navigation

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory


sealed class Routes(val route: String) {

    /* Root graphs
    * If you did NOT have Root, you would need to pop each screen manually (bad design).
    * Clean separation of flows
    * Ability to clear entire auth stack at once
    * Predictable back behavior
    * Scalable architecture
    * */
    object AuthRoot : Routes("auth_root")
    object MainRoot : Routes("main_root")
    //Auth
    data object Login : Routes("login")
    data object Register : Routes("register")
    data object ResetPassword : Routes("forgot_password")

    //Main
    data object Home : Routes("home")
    data object AddExpense : Routes("add_expense")
    data object EditExpense : Routes("edit_expense/{expenseId}"){
        fun createRoute(expenseId: String): String {
            return "edit_expense/$expenseId"
        }
    }


    object MonthlySummary : Routes("monthly_summary/{year}/{month}") {

        fun routeWithMonth(year: Int, month: Int): String {
            return "monthly_summary/$year/$month"
        }
    }

    // Expenses
    object MonthlyExpenses : Routes("monthly_expenses")

    // Expenses Details
    object ExpenseDetail : Routes("expense_detail/{expenseId}"){
        fun createRoute(expenseId: String): String {
            return "expense_detail/$expenseId"
        }
    }


    object CategoryDetail : Routes(
        "category_detail/{category}/{year}/{month}"
    ) {
        fun createRoute(
            category: ExpenseCategory,
            year: Int,
            month: Int
        ): String {
            return "category_detail/${category.name}/$year/$month"
        }
    }

    data object Profile : Routes("profile")
    data object Settings : Routes("settings")
}