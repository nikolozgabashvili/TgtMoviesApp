package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class RegisterError(
    @SerializedName("Errors")
    val errors: List<String?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
)