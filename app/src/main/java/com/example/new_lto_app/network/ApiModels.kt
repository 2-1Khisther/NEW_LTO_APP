package com.example.new_lto_app.network

// JSON shapes sent to / received from the API. Field names must match the API exactly.

data class HealthResponse(
    val status: String,
    val contentDomains: Int
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val targetLicense: String   // "NON_PROF" or "PROF"
)

data class UserDto(
    val userId: Int,
    val fullName: String,
    val email: String,
    val targetLicense: String,
    val languagePref: String,
    val isOnboarded: Boolean,
    val currentStreak: Int,
    val longestStreak: Int
)

data class AuthResponse(
    val token: String,
    val user: UserDto
)
