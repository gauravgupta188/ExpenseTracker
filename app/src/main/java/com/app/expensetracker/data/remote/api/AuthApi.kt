package com.app.expensetracker.data.remote.api

import com.app.expensetracker.data.remote.model.LoginRequest
import com.app.expensetracker.data.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

}