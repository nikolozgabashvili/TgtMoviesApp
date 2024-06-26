package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.PersonDetails
import com.example.tgtmoviesapp.application.domain.repository.CelebritiesRepository
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Person>> {
        return repository.getPopularPeople(Constants.API_KEY, page = page)
    }

}

class SearchPeopleUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(query:String,page:Int  = 1): Flow<Resource<Person>> {

        return repository.searchPeople(query,page)

    }

}

class GetTrendingPeopleUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Person>> {
        return repository.getTrendingPeople(Constants.API_KEY, page = page)
    }

}
class GetPeopleDetailsUseCase @Inject constructor(private val repository: CelebritiesRepository) {

    suspend fun execute(id:Int): Flow<Resource<PersonDetails>> {
        return repository.getPersonDetails(id)
    }

}






