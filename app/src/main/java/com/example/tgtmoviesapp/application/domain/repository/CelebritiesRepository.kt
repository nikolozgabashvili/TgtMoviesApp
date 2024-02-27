package com.example.tgtmoviesapp.application.domain.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface CelebritiesRepository {
    suspend fun getPopularPeople(apiKey:String?): Flow<Resource<Person>>
    suspend fun getTrendingPeople(apiKey:String?): Flow<Resource<Person>>
}