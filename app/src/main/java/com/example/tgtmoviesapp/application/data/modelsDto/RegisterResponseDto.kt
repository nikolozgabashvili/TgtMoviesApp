package com.example.tgtmoviesapp.application.data.modelsDto

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
    val email: String?,
    val id: String?,
    val password: String?,
    val userFavoriteMovies: List<Int?>?,
    val username: String?
)