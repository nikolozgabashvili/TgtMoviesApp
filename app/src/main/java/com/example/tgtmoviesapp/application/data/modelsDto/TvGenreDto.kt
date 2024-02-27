package com.example.tgtmoviesapp.application.data.modelsDto

data class TvGenreDto(
    val genres: List<GenreDto?>?
) {
    data class GenreDto(
        val id: Int?,
        val name: String?
    )
}