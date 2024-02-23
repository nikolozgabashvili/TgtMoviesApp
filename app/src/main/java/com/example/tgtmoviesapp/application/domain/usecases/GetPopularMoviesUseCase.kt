package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<PopularMovies>> {
        return repository.getPopularMovies(API_KEY)
    }

}

class GetUpcomingMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<PopularMovies>> {
        return repository.getUpcomingMovies(API_KEY)
    }

}