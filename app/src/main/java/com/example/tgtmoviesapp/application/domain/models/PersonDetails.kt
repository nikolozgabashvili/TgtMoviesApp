package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class PersonDetails(
    val adult: Boolean?,
    val biography: String?,
    val birthday: String?,
    val deathDay: String?,
    val gender: Int?,
    val homepage: String?,
    val id: Int?,
    val imdbId: String?,
    val knownForDepartment: String?,
    val name: String?,
    val placeOfBirth: String?,
    val popularity: Double?,
    val profilePath: String?
)