package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovies
import com.example.tgtmoviesapp.application.data.remote.mappers.toUpcomingMovies
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
) : Repository {
    override suspend fun getPopularMovies(apiKey:String?): Flow<Resource<Movies>>  = flow {

        try {
            val response = api.getPopularMovies(API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getTopRatedMovies(apiKey: String?): Flow<Resource<Movies>> =flow{

        try {
            val response = api.getTopRatedMovies(apiKey=API_KEY)
            emit(Resource.Loading())

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getUpcomingMovies(apiKey:String?): Flow<Resource<Movies>> = flow {

        try {
            val response = api.getUpcomingMovies(apiKey= API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getPITMovies(apiKey: String?): Flow<Resource<Movies>> =flow {
        try {
            val response = api.getPITMovies(apiKey= API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getTrendingMovies(apiKey: String?): Flow<Resource<Movies>> = flow {
        try {
            val response = api.getTrendingMovies(apiKey= API_KEY)
            emit(Resource.Loading())

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovies()))
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