package com.example.tgtmoviesapp.application.commons.resource

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val loading: Boolean? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null,loading:Boolean? = false) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

