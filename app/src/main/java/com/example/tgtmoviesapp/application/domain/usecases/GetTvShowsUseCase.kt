package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.constants.Constants
import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.repository.Repository
import com.example.tgtmoviesapp.application.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import java.text.ParseException
import javax.inject.Inject

class GetPopularTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(page:Int = 1): Flow<Resource<TvShows>> {
        return repository.getPopularTvShows(apiKey = API_KEY, page = page)
    }
}
class GetUpcomingTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(page:Int = 1): Flow<Resource<TvShows>> {
        return repository.getUpcomingTvShows(apiKey = API_KEY, page = page)
    }
}
class GetTrendingTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(page:Int = 1): Flow<Resource<TvShows>> {
        return repository.getTrendingTvShows(apiKey = API_KEY, page = page)
    }
}
class GetTopRatedTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(page:Int = 1): Flow<Resource<TvShows>> {
        return repository.getTopRatedTvShows(apiKey = API_KEY, page = page)
    }
}

class SearchTvShowsUseCase @Inject constructor(private val repository: TvShowsRepository) {
    suspend fun execute(query:String,page:Int = 1): Flow<Resource<TvShows>> {
        return repository.searchTvShows(query,page)
    }
}

class GetTvGenresUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute():Flow<Resource<TvGenre>>{
        return repository.getTvGenres(API_KEY)
    }
}

class GetTvDetailsUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute(id:Int):Flow<Resource<MovieDetails>>{
        return repository.getTvDetails(id)
    }
}

class GetSimilarShowsUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute(id:Int,page:Int):Flow<Resource<TvShows>>{
        return repository.getSimilarShows(id,page)
    }
}

class GetRecommendedTvUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute(id:Int,page:Int):Flow<Resource<TvShows>>{
        return repository.getRecommendedShows(id,page)
    }
}

class GetTvVideosUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute(id:Int):Flow<Resource<MovieVideo>>{
        return repository.getTvVideos(id)
    }
}

class GetTvCastUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute(id:Int):Flow<Resource<Person>>{
        return repository.getTvCast(id)
    }
}
class GetPersonTvShowCreditsUseCase @Inject constructor(private val repository: TvShowsRepository){
    suspend fun execute(id:Int):Flow<Resource<TvShows>>{
        return repository.getShowByPersonId(id)
    }
}