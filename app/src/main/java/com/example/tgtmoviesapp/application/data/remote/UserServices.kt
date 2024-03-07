package com.example.tgtmoviesapp.application.data.remote


import com.example.tgtmoviesapp.application.data.modelsDto.LoginClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserServices {
    @POST("/api/Users/Register")
    suspend fun registerUser(
        @Body username: String,
        password: String,
        email: String
    ): Response<String>

    @POST("/api/Users/Login")
    @Headers("accept: application/json")
    suspend fun loginUser(
        @Body body: LoginClass,
    ): Response<String>

}