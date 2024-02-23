package com.example.tgtmoviesapp.application.domain.repository


import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.modelsDto.PopularMoviesDto
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getPopularMovies(apiKey:String?):Flow<Resource<PopularMovies>>
    suspend fun getUpcomingMovies(apiKey:String?):Flow<Resource<UpcomingMovies>>

}