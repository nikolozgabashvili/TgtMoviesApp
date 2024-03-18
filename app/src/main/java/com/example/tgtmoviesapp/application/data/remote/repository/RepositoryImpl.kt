package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toAllItemModel
import com.example.tgtmoviesapp.application.data.remote.mappers.toGenre
import com.example.tgtmoviesapp.application.data.remote.mappers.toLanguage
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovieDetails
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovieVideo
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovies
import com.example.tgtmoviesapp.application.data.remote.mappers.toPerson
import com.example.tgtmoviesapp.application.data.remote.mappers.toStringList
import com.example.tgtmoviesapp.application.domain.models.AllItemModel
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Languages
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieGenre
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
) : Repository {
    override suspend fun getPopularMovies(apiKey:String?,page:Int): Flow<Resource<Movies>>  = flow {

        try {
            emit(Resource.Loading(loading = true))
            val response = api.getPopularMovies(API_KEY, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTopRatedMovies(apiKey: String?,page:Int): Flow<Resource<Movies>> =flow{

        try {
            emit(Resource.Loading(loading = true))
            val response = api.getTopRatedMovies(apiKey=API_KEY, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getUpcomingMovies(apiKey:String?,page:Int): Flow<Resource<Movies>> = flow {

        try {
            emit(Resource.Loading(loading = true))
            val response = api.getUpcomingMovies(apiKey= API_KEY, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getPITMovies(apiKey: String?,page:Int): Flow<Resource<Movies>> =flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getPITMovies(apiKey= API_KEY, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getMovieGenres(apiKey: String?): Flow<Resource<MovieGenre>> = flow {

        try {
            emit(Resource.Loading(loading = true))
            val response = api.getMovieGenres(apiKey= API_KEY)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toGenre()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun searchMovie(query: String,page:Int): Flow<Resource<Movies>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.searchMovies(query = query, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getSearchResults(query: String): Flow<Resource<AllItemModel>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getSearchResults(query=query)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toAllItemModel()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getMovieById(id: Int): Flow<Resource<MovieDetails>> =flow{
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getDetails(movieId = id)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovieDetails()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getSimilarMovies(id: Int, page: Int): Flow<Resource<Movies>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getSimilarMovies(movieId = id, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getRecommendedMovies(id: Int, page: Int): Flow<Resource<Movies>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getRecommendedMovies(movieId = id, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getMovieVideos(id: Int): Flow<Resource<MovieVideo>> =flow{
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getMovieVideos(movieId = id)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovieVideo()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getMovieCast(id: Int): Flow<Resource<Person>> =flow{
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getMovieCast(movieId = id)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toPerson()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getLanguages(): Flow<Resource<Languages>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getLanguages()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toLanguage()))
            } else {
                emit(Resource.Error(response.message().toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTrendingMovies(apiKey: String?,page:Int): Flow<Resource<Movies>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = api.getTrendingMovies(apiKey= API_KEY, page = page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }


}