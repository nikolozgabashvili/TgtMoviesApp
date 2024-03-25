package com.example.tgtmoviesapp.application.commons.resource

sealed class Resource<T>(
    val data: T? = null,
    val message: List<String?>? = null,
    val loading: Boolean? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(loading:Boolean? = true) : Resource<T>(null,null,loading)
    class Error<T>(message: List<String?>?, data: T? = null) : Resource<T>(data, message)
}

