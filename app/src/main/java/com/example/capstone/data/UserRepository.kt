package com.example.capstone.data

import com.example.capstone.data.retrofit.LoginRequest
import com.example.capstone.data.pref.UserModel
import com.example.capstone.data.pref.UserPreference
import com.example.capstone.data.retrofit.ApiService
import com.example.capstone.data.retrofit.RegisterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun login(email: String, password: String): Flow<Result<UserModel>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (!response.error) {
                val user = UserModel(
                    userId = response.loginResult.userId,
                    name = response.loginResult.name,
                    email = email,
                    token = response.loginResult.token,
                    isLogin = true
                )
                userPreference.saveSession(user) 
                emit(Result.Success(user))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    suspend fun register(name: String, email: String, password: String): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(RegisterRequest(name, email, password))
            if (!response.error) {
                emit(Result.Success(response.message))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    suspend fun updateUserProfile(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }


    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun logout() {
        try {
            // Hapus data session
            userPreference.logout()
        } catch (e: Exception) {
            throw e
        }
    }

    fun getToken(): Flow<String> {
        return userPreference.getToken()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}