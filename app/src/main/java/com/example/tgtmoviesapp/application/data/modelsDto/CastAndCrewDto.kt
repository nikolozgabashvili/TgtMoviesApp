package com.example.tgtmoviesapp.application.data.modelsDto

import com.google.gson.annotations.SerializedName

data class CastAndCrewDto(
    val cast: List<Cast?>?,
    val crew: List<Crew?>?,
    val id: Int?
) {
    data class Cast(
        val adult: Boolean?,
        @SerializedName("cast_id")
        val castId: Int?,
        val character: String?,
        @SerializedName("credit_id")
        val creditId: String?,
        val gender: Int?,
        val id: Int?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        val name: String?,
        val order: Int?,
        @SerializedName("original_name")
        val originalName: String?,
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?
    )

    data class Crew(
        val adult: Boolean?,
        @SerializedName("credit_id")
        val creditId: String?,
        val department: String?,
        val gender: Int?,
        val id: Int?,
        val job: String?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        val name: String?,
        @SerializedName("original_name")
        val originalName: String?,
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?
    )
}