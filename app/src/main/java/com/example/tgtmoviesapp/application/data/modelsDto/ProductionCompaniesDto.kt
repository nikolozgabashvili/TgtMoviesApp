package com.example.tgtmoviesapp.application.data.modelsDto

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesDto(
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String?
)
