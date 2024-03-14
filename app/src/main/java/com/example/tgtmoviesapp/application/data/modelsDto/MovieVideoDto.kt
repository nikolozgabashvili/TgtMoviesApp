package com.example.tgtmoviesapp.application.data.modelsDto

import com.google.gson.annotations.SerializedName

data class MovieVideoDto(
    val id: Int?,
    val results: List<Result?>?
) {
    data class Result(
        val id: String?,
        @SerializedName("iso_3166_1")
        val iso31661: String?,
        @SerializedName("iso_639_1")
        val iso6391: String?,
        val key: String?,
        val name: String?,
        val official: Boolean?,
        @SerializedName("published_at")
        val publishedAt: String?,
        val site: String?,
        val size: Int?,
        val type: String?
    )
}