package com.example.tgtmoviesapp.application.data.modelsDto

data class MovieGenreDto(
    val genres: List<Genre?>?
) {
    data class Genre(
        val id: Int?,
        val name: String?
    )
}