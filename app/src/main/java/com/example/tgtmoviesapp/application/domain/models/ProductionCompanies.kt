package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class ProductionCompanies(
    val id: Int?,
    val logoPath: String?,
    val name: String,
    val originCountry: String?
)
