package com.example.tgtmoviesapp.application.data.remote

import com.example.tgtmoviesapp.application.data.modelsDto.MoviesDto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesDto>


    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<MoviesDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<MoviesDto>



    @GET("movie/now_playing")
    suspend fun getPITMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<MoviesDto>
    @GET("trending/movie/day")

    suspend fun getTrendingMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<MoviesDto>




}