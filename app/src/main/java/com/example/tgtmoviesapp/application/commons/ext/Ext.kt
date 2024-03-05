package com.example.tgtmoviesapp.application.commons.ext

private fun <T> Array<T>.isAllTrue(): Boolean {
    return this.all {
        it == true
    }
}