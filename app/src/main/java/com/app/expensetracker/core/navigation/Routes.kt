package com.app.expensetracker.core.navigation


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
    data object ExpenseSummary : Routes("expense_summary")
    data object Profile : Routes("profile")
    data object Settings : Routes("settings")
}