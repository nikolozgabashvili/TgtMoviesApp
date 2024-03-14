package com.example.tgtmoviesapp.application.data.modelsDto

data class FavouriteMoviesDto(
    val page: Int?,
    val totalPage: Int?,
    val userFavoriteMovies: List<UserFavoriteMovies?>?
)