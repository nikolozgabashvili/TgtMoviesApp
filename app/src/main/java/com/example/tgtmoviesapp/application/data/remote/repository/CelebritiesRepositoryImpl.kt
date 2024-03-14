package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovies
import com.example.tgtmoviesapp.application.data.remote.mappers.toPerson
import com.example.tgtmoviesapp.application.data.remote.mappers.toStringList
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.repository.CelebritiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CelebritiesRepositoryImpl @Inject constructor(val api: Api): CelebritiesRepository {
    override suspend fun getPopularPeople(apiKey: String?,page:Int): Flow<Resource<Person>> = flow{
        try {
            emit(Resource.Loading(null))
            val response = api.getPopularPeople(apiKey = API_KEY, page = page)

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

    override suspend fun getTrendingPeople(apiKey: String?,page:Int): Flow<Resource<Person>> =flow {
        try {
            emit(Resource.Loading(null))
            val response = api.getTrendingPerson(apiKey = API_KEY, page = page)

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

    override suspend fun searchPeople(query: String,page:Int): Flow<Resource<Person>> = flow {
        try {
            emit(Resource.Loading(null))
            val response = api.searchPerson(query = query, page = page)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toPerson()))
            } else {

                emit(Resource.Error("error".toStringList(), null))

            }
        } catch (e: Exception) {
            emit(Resource.Error("$e".toStringList(), null))

        }
    }
}