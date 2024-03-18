package com.example.tgtmoviesapp.application.data.modelsDto

import com.google.gson.annotations.SerializedName

class LanguagesDto(
    val languages: List<LanguagesDtoItem>
) {
    data class LanguagesDtoItem(
        @SerializedName("english_name")
        val englishName: String?,
        @SerializedName("iso_639_1")
        val langCode: String?,
        val name: String?
    )
}