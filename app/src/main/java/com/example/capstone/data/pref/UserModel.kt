package com.example.capstone.data.pref

data class UserModel(
    val userId: String,
    val name: String,
    val email: String,
    val token: String,
    val photoUrl: String = "",
    val isLogin: Boolean = false
)