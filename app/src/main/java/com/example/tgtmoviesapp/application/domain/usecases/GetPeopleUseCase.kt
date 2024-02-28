package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.repository.CelebritiesRepository
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(): Flow<Resource<Person>> {
        return repository.getPopularPeople(Constants.API_KEY)
    }

}

class SearchPeopleUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(): Flow<Resource<Person>> {

        return repository.searchPeople()

    }

}

class GetTrendingPeopleUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(): Flow<Resource<Person>> {
        return repository.getTrendingPeople(Constants.API_KEY)
    }

}



