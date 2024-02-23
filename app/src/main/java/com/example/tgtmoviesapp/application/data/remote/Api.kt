package com.example.tgtmoviesapp.application.data.remote

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.data.modelsDto.PopularMoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Response<PopularMoviesDto>


    @GET("tv/on_the_air")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<PopularMoviesDto>


}