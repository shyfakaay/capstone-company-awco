package com.example.capstone.data.retrofit

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val message: String,
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String,
)