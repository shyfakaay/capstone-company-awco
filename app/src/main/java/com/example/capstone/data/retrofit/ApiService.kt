package com.example.capstone.data.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequest): LoginResponse

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part photo: MultipartBody.Part? = null
    ): RegisterResponse
}