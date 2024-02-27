package com.example.tgtmoviesapp.application.domain.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    suspend fun getPopularTvShows(apiKey:String?): Flow<Resource<TvShows>>
    suspend fun getTopRatedTvShows(apiKey:String?): Flow<Resource<TvShows>>
    suspend fun getUpcomingTvShows(apiKey:String?): Flow<Resource<TvShows>>
    suspend fun getTrendingTvShows(apiKey:String?): Flow<Resource<TvShows>>

    //------------
    suspend fun getTvGenres(apiKey: String?):Flow<Resource<TvGenre>>
}