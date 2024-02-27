package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(): Flow<Resource<TvShows>> {
        return repository.getPopularTvShows(apiKey = API_KEY)
    }
}
class GetUpcomingTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(): Flow<Resource<TvShows>> {
        return repository.getUpcomingTvShows(apiKey = API_KEY)
    }
}
class GetTrendingTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(): Flow<Resource<TvShows>> {
        return repository.getTrendingTvShows(apiKey = API_KEY)
    }
}
class GetTopRatedTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(): Flow<Resource<TvShows>> {
        return repository.getTopRatedTvShows(apiKey = API_KEY)
    }
}

class GetTvGenresUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute():Flow<Resource<TvGenre>>{
        return repository.getTvGenres(API_KEY)
    }
}