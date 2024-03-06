package com.example.tgtmoviesapp.application.data.remote

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.data.modelsDto.AllItemModelDto
import com.example.tgtmoviesapp.application.data.modelsDto.MovieGenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.MoviesDto
import com.example.tgtmoviesapp.application.data.modelsDto.PersonDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvGenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvShowsDto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MoviesDto>


    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MoviesDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MoviesDto>


    @GET("movie/now_playing")
    suspend fun getPITMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MoviesDto>

    @GET("trending/movie/day")

    suspend fun getTrendingMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MoviesDto>


    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<TvShowsDto>


    @GET("tv/airing_today")
    suspend fun getUpcomingTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<TvShowsDto>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<TvShowsDto>


    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<TvShowsDto>

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<PersonDto>

    @GET("trending/person/day")
    suspend fun getTrendingPerson(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<PersonDto>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") lang: String = "en",
        @Query("api_key") apiKey: String
    ): Response<MovieGenreDto>


    @GET("genre/tv/list")
    suspend fun getTvGenre(
        @Query("language") lang: String = "en",
        @Query("api_key") apiKey: String
    ): Response<TvGenreDto>


    @GET("search/multi")
    suspend fun getSearchResults(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String?
    ): Response<AllItemModelDto>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<MoviesDto>

    @GET("search/person")
    suspend fun searchPerson(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<PersonDto>

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<TvShowsDto>

    @GET("/movie/{movie_id}?language=en-US")
    suspend fun getMovieById(
        @Query("api_key") apiKey: String = API_KEY,
        @Path("movie_id") movieId:Int
    )

}