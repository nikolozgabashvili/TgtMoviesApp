package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    val email: String?,
    val id: String?,
    val password: String?,
    val userFavoriteMovies: List<Int?>?,
    val username: String?
)