package com.example.tgtmoviesapp.application.data.modelsDto

import com.google.gson.annotations.SerializedName

data class PersonDto(
    val page: Int?,
    val results: List<Result?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
) {
    data class Result(
        val adult: Boolean?,
        val gender: Int?,
        val id: Int?,
        @SerializedName("known_for")
        val knownFor: List<KnownFor?>?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        @SerializedName("media_type")
        val mediaType: String?,
        val name: String?,
        @SerializedName("original_name")
        val originalName: String?,
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?
    ) {
        data class KnownFor(
            val adult: Boolean?,
            @SerializedName("backdrop_path")
            val backdropPath: String?,
            @SerializedName("first_air_date")
            val firstAirDate: String?,
            @SerializedName("genre_ids")
            val genreIds: List<Int?>?,
            val id: Int?,
            @SerializedName("media_type")
            val mediaType: String?,
            val name: String?,
            @SerializedName("origin_country")
            val originCountry: List<String?>?,
            @SerializedName("original_language")
            val originalLanguage: String?,
            @SerializedName("original_name")
            val originalName: String?,
            @SerializedName("original_title")
            val originalTitle: String?,
            val overview: String?,
            val popularity: Double?,
            @SerializedName("poster_path")
            val posterPath: String?,
            @SerializedName("release_date")
            val releaseDate: String?,
            val title: String?,
            val video: Boolean?,
            @SerializedName("vote_average")
            val voteAverage: Double?,
            @SerializedName("vote_count")
            val voteCount: Int?
        )
    }
}