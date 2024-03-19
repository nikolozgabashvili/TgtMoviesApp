package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class Creators(
    val id: Int?,

    val creditId: String?,
    val name: String?,
    val gender: String?,
    val profilePath: String?
)



data class MovieDetails(
    val adult: Boolean?,
    val network: List<String>?,
    val backdropPath: String?,
    val createdBy: List<Creators>?,
    val budget: Int?,
    val genres: List<Genre?>?,
    val homepage: String?,
    val id: Int?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val spokenLanguage: String?,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val productionCompanies: List<ProductionCompanies>?,
    val firstAirDate:String?,
    val originCountry: List<String>?,
    val name:String?
)

