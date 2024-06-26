package com.example.tgtmoviesapp.application.domain.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.PersonDetails
import kotlinx.coroutines.flow.Flow

interface CelebritiesRepository {
    suspend fun getPopularPeople(apiKey:String?,page:Int = 1): Flow<Resource<Person>>
    suspend fun getTrendingPeople(apiKey:String?,page:Int = 1): Flow<Resource<Person>>

    suspend fun searchPeople(query: String,page:Int):Flow<Resource<Person>>

    suspend fun getPersonDetails(id:Int):Flow<Resource<PersonDetails>>


}