package com.example.tgtmoviesapp.application.domain.repository


import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.modelsDto.AllItemModelDto
import com.example.tgtmoviesapp.application.domain.models.AllItemModel
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieGenre
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface Repository {

    suspend fun getPopularMovies(apiKey:String?,page:Int = 1):Flow<Resource<Movies>>
    suspend fun getTopRatedMovies(apiKey:String?,page:Int = 1):Flow<Resource<Movies>>
    suspend fun getUpcomingMovies(apiKey:String?,page:Int = 1):Flow<Resource<Movies>>
    suspend fun getTrendingMovies(apiKey:String?,page:Int = 1):Flow<Resource<Movies>>
    suspend fun getPITMovies(apiKey:String?,page:Int = 1):Flow<Resource<Movies>>
    ///-----------------------------

    suspend fun getMovieGenres(apiKey: String?):Flow<Resource<MovieGenre>>
    suspend fun searchMovie(query: String,page:Int):Flow<Resource<Movies>>

    suspend fun getSearchResults(query: String):Flow<Resource<AllItemModel>>

    suspend fun getMovieById(id:Int):Flow<Resource<MovieDetails>>

    suspend fun getSimilarMovies(id:Int,page: Int = 1):Flow<Resource<Movies>>

    suspend fun getRecommendedMovies(id:Int,page: Int = 1):Flow<Resource<Movies>>

    suspend fun getMovieVideos(id:Int):Flow<Resource<MovieVideo>>
}