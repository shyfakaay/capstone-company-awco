package com.example.capstone.data

import com.example.capstone.data.retrofit.ApiService
import com.example.capstone.data.retrofit.LoginRequest
import com.example.capstone.data.retrofit.RegisterRequest
import com.example.capstone.data.pref.UserModel
import com.example.capstone.data.pref.UserPreference
import okhttp3.RequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    // Fungsi login yang diperbaiki
    suspend fun login(email: String, password: String): Flow<Result<UserModel>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))

            // Periksa jika response berhasil dan simpan session
            if (response.token.isNotEmpty()) {
                val user = UserModel(
                    userId = response.token,  // Asumsi 'token' adalah userId
                    name = response.message,  // Menggunakan message untuk nama
                    email = email,
                    token = response.token,
                    isLogin = true
                )
                saveSession(user)  // Memanggil saveSession di sini
                emit(Result.Success(user))
            } else {
                emit(Result.Error("Invalid credentials"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }


    /// Fungsi register yang diperbaiki
    suspend fun register(name: String, email: String, password: String): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val nameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
            val emailRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
            val passwordRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), password)

            val response = apiService.register(nameRequestBody, emailRequestBody, passwordRequestBody)
            emit(Result.Success(response.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Registration failed"))
        }
    }


    // Fungsi untuk memperbarui profile user
    suspend fun updateUserProfile(user: UserModel) {
        userPreference.saveSession(user)
    }

    // Fungsi untuk mendapatkan session user
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    // Fungsi untuk logout user
    suspend fun logout() {
        try {
            // Hapus session user
            userPreference.logout()
        } catch (e: Exception) {
            throw e
        }
    }

    // Fungsi untuk menyimpan session
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)  // Menyimpan data session ke UserPreference
    }

    // Fungsi untuk mendapatkan token
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