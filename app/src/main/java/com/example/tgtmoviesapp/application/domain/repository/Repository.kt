package com.example.tgtmoviesapp.application.domain.repository


import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getPopularMovies(apiKey:String?):Flow<Resource<Movies>>
    suspend fun getTopRatedMovies(apiKey:String?):Flow<Resource<Movies>>
    suspend fun getUpcomingMovies(apiKey:String?):Flow<Resource<Movies>>
    suspend fun getPITMovies(apiKey:String?):Flow<Resource<Movies>>
    suspend fun getTrendingMovies(apiKey:String?):Flow<Resource<Movies>>

}