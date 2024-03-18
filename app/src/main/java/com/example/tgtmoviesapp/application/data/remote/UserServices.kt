package com.example.tgtmoviesapp.application.data.remote


import com.example.tgtmoviesapp.application.data.modelsDto.CheckFavouritesDto
import com.example.tgtmoviesapp.application.data.modelsDto.FavouriteMoviesDto
import com.example.tgtmoviesapp.application.data.modelsDto.GetCurrentUserDto
import com.example.tgtmoviesapp.application.data.modelsDto.LoginClass
import com.example.tgtmoviesapp.application.data.modelsDto.RegisterClass
import com.example.tgtmoviesapp.application.data.modelsDto.RegisterResponseDto
import com.example.tgtmoviesapp.application.domain.models.FavMovieId
import com.example.tgtmoviesapp.application.domain.models.MovieAdd
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserServices {
    @POST("/api/Users/Register")
    @Headers("accept: application/json")
    suspend fun registerUser(
        @Body body:RegisterClass
    ): Response<RegisterResponseDto>

    @POST("/api/Users/Login")
    @Headers("accept: application/json")
    suspend fun loginUser(
        @Body body: LoginClass
    ): Response<String>


    @GET("/api/Users/CurrentUser")
    @Headers("accept: application/json")
    suspend fun getCurrentUser(
        @Header("Authorization") bearer:String
    ): Response<GetCurrentUserDto>


    @GET("api/Favorites/{page}")
    @Headers("accept: application/json")
    suspend fun getFavouriteMovies(
        @Header("Authorization") bearer:String,
        @Path("page") page:Int = 1
    ): Response<FavouriteMoviesDto>


    @POST("api/Favorites/AddFavorite")
    @Headers("accept: application/json")
    suspend fun addFavourite(
        @Header("Authorization") bearer:String,
        @Body movieId:MovieAdd
    ): Response<MovieAdd>

    @DELETE("api/Favorites/{id}")
    @Headers("accept: application/json")
    suspend fun deleteFavourite(
        @Header("Authorization") bearer:String,
        @Path("id") id:Int
    ): Response<Unit>


    @GET("api/Favorites/CheckFavoriteMovie/{movie_id}")
    @Headers("accept: application/json")
    suspend fun isFavourite(
        @Header("Authorization") bearer:String,
        @Path("movie_id") id:Int
    ): Response<CheckFavouritesDto>

}