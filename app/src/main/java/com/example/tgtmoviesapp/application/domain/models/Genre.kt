package com.example.tgtmoviesapp.application.domain.models

data class MovieGenre(
    val genres: List<Genre?>?
) {

}

data class Genre(
    val id: Int?,
    val name: String?
)