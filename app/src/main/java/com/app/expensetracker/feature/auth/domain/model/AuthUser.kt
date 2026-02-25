package com.app.expensetracker.feature.auth.domain.model

data class AuthUser(
    val uid:String,
    val email:String?,
    val displayName:String?
)
