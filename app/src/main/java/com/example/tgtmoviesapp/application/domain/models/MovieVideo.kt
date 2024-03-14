package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class MovieVideo(
    val id: Int?,
    val results: List<Result?>?
) {
    data class Result(
        val key: String?
    )
}