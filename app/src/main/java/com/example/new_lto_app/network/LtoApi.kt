package com.example.new_lto_app.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// The API endpoints the app calls. More are added in the next stages
// (content sync, exam upload, profile).
interface LtoApi {

    @GET("api/health")
    suspend fun health(): HealthResponse

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest): AuthResponse
}
