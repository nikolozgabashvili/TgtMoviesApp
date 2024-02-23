package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toPopularMovies
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api
) : Repository {
    override suspend fun getPopularMovies(apiKey:String?): Flow<Resource<PopularMovies>>  = flow {

        try {
            val response = api.getPopularMovies(API_KEY)
            emit(Resource.loading(null))

            if (response.isSuccessful) {
                emit(Resource.success(response.body()?.toPopularMovies()))
            } else {
                println("not successfuul")
                emit(Resource.error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.error("$e", null))

        }
    }

    override suspend fun getUpcomingMovies(apiKey:String?): Flow<Resource<PopularMovies>> = flow {

        try {
            val response = api.getUpcomingMovies(apiKey= API_KEY)
            emit(Resource.loading(null))

            if (response.isSuccessful) {
                emit(Resource.success(response.body()?.toPopularMovies()))
            } else {
                println("not successfuul")
                emit(Resource.error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.error("$e", null))

        }
    }



}