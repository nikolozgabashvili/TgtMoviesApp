package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovies
import com.example.tgtmoviesapp.application.data.remote.mappers.toTvGenre
import com.example.tgtmoviesapp.application.data.remote.mappers.toTvShows
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(
    private val api: Api
):TvShowsRepository {

    override suspend fun getPopularTvShows(apiKey: String?): Flow<Resource<TvShows>> = flow {

        try {
            val response = api.getPopularTvShows(API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getTopRatedTvShows(apiKey: String?): Flow<Resource<TvShows>> = flow {

        try {
            val response = api.getTopRatedTvShows(apiKey = API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getUpcomingTvShows(apiKey: String?): Flow<Resource<TvShows>> = flow {

        try {
            val response = api.getUpcomingTvShows(apiKey = API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getTrendingTvShows(apiKey: String?): Flow<Resource<TvShows>> = flow {

        try {
            val response = api.getTrendingTvShows(apiKey = API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvShows()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getTvGenres(apiKey: String?): Flow<Resource<TvGenre>> =flow {

        try {
            val response = api.getTvGenre(apiKey = API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toTvGenre()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }

        }
        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }
}