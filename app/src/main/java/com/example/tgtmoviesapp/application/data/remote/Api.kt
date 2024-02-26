package com.example.tgtmoviesapp.application.data.remote

import com.example.tgtmoviesapp.application.data.modelsDto.MoviesDto
import com.example.tgtmoviesapp.application.data.modelsDto.PersonDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvShowsDto

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


    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String
    ): Response<TvShowsDto>


    @GET("tv/airing_today")
    suspend fun getUpcomingTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<TvShowsDto>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<TvShowsDto>


    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<TvShowsDto>

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<PersonDto>

    @GET("trending/person/day")
    suspend fun getTrendingPerson (
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String
    ): Response<PersonDto>




}