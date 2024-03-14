package com.example.tgtmoviesapp.application.data.modelsDto

data class GetCurrentUserDto(
    val email: String?,
    val id: String?,
    val password: String?,
    val userFavoriteMovies: List<UserFavoriteMovies?>?,
    val username: String?
)

data class UserFavoriteMovies(
    val movieId: Int?
)