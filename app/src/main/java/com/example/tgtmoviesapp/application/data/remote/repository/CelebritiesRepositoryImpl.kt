package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovies
import com.example.tgtmoviesapp.application.data.remote.mappers.toPerson
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.repository.CelebritiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CelebritiesRepositoryImpl @Inject constructor(val api: Api): CelebritiesRepository {
    override suspend fun getPopularPeople(apiKey: String?): Flow<Resource<Person>> = flow{
        try {
            val response = api.getPopularPeople(apiKey = API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toPerson()))
            } else {
                println("not successfuul")
                emit(Resource.Error("error", null))

            }
        }

        catch (e:Exception){
            emit(Resource.Error("$e", null))

        }
    }

    override suspend fun getTrendingPeople(apiKey: String?): Flow<Resource<Person>> =flow {
        try {
            val response = api.getTrendingPerson(apiKey = API_KEY)
            emit(Resource.Loading(null))

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toPerson()))
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