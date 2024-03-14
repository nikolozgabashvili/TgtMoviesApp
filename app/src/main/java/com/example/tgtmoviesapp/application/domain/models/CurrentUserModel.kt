package com.example.tgtmoviesapp.application.domain.models

data class CurrentUserModel(
    val email: String?,
    val id: String?,
    val password: String?,
    val userFavoriteMovies: List<UserFavoriteMovies?>?,
    val username: String?
) {
    data class UserFavoriteMovies(
        val movieId: Int?
    )
}
