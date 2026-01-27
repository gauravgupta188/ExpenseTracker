package com.app.expensetracker.core.utils

fun formatCurrency(amount: Double): String =
    "₹%,.2f".format(amount)


