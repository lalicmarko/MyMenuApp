package com.lalic.marko.mymenu.api;

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers(
        "application: mobile-application",
        "Content-Type: application/json",
        "Device-UUID: 123456",
        "Api-Version: 3.7.0"
    )
    @POST("api/customers/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val accessToken: String)
