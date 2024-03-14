package com.example.tgtmoviesapp.application.domain.models



data class FavouriteMovies(
    val page: Int?,
    val totalPage: Int?,
    val userFavoriteMovies: List<UserFavoriteMovies?>?
)

data class UserFavoriteMovies(
    val movieId: Int?
)

