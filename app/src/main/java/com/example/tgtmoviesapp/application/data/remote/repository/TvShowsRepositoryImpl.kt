package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovieDetails
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovieVideo
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovies
import com.example.tgtmoviesapp.application.data.remote.mappers.toPerson
import com.example.tgtmoviesapp.application.data.remote.mappers.toStringList
import com.example.tgtmoviesapp.application.data.remote.mappers.toTvGenre
import com.example.tgtmoviesapp.application.data.remote.mappers.toTvShows
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(
    private val api: Api
):TvShowsRepository {

    override suspend fun getPopularTvShows(apiKey: String?,page:Int): Flow<Resource<TvShows>> = flow {

        try {
            emit(Resource.Loading(null))
            val response = api.getPopularTvShows(API_KEY, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTopRatedTvShows(apiKey: String?,page:Int ): Flow<Resource<TvShows>> = flow {

        try {
            emit(Resource.Loading(null))
            val response = api.getTopRatedTvShows(apiKey = API_KEY, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getUpcomingTvShows(apiKey: String?,page:Int ): Flow<Resource<TvShows>> = flow {

        try {
            emit(Resource.Loading(null))
            val response = api.getUpcomingTvShows(apiKey = API_KEY, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTrendingTvShows(apiKey: String?,page:Int): Flow<Resource<TvShows>> = flow {

        try {
            emit(Resource.Loading(null))
            val response = api.getTrendingTvShows(apiKey = API_KEY, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTvGenres(apiKey: String?): Flow<Resource<TvGenre>> =flow {

        try {
            emit(Resource.Loading(null))
            val response = api.getTvGenre(apiKey = API_KEY)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvGenre()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun searchTvShows(query: String,page:Int): Flow<Resource<TvShows>> =flow {

        try {
            emit(Resource.Loading(null))
            val response = api.searchTvShows(apiKey = API_KEY, query = query, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTvDetails(id: Int): Flow<Resource<MovieDetails>> =flow{
        try {
            emit(Resource.Loading(null))
            val response = api.getTvDetails(id)

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

    override suspend fun getSimilarShows(id: Int, page: Int): Flow<Resource<TvShows>> =flow {
        try {
            emit(Resource.Loading(null))
            val response = api.getSimilarShows(movieId = id, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getRecommendedShows(id: Int, page: Int): Flow<Resource<TvShows>> = flow{
        try {
            emit(Resource.Loading(null))
            val response = api.getRecommendedShows(id, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                emit(Resource.Error("error".toStringList(), null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e".toStringList(), null))

        }
    }

    override suspend fun getTvVideos(id: Int): Flow<Resource<MovieVideo>> = flow {
        try {
            emit(Resource.Loading(null))
            val response = api.getTvVideos(id)

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

    override suspend fun getTvCast(id: Int): Flow<Resource<Person>> = flow{
        try {
            emit(Resource.Loading(null))
            val response = api.getTvCast(id)

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
}