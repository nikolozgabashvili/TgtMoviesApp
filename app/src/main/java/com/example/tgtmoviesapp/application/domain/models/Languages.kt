package com.example.tgtmoviesapp.application.domain.models

import com.google.gson.annotations.SerializedName

data class Languages (
    val languages: List<LanguagesDtoItem>
) {
    data class LanguagesDtoItem(
        val englishName: String?,
        val langCode: String?,
        val name: String?
    )
}
