package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.constants.Constants.API_KEY
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.AllItemModel
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.Async.Execute
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<Movies>> {
        return repository.getPopularMovies(API_KEY)
    }

}

class GetUpcomingMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<Movies>> {
        return repository.getUpcomingMovies(API_KEY)
    }

}



class GetTopRatedMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<Movies>> {
        return repository.getTopRatedMovies(apiKey = API_KEY)
    }

}
class GetPITMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<Movies>> {
        return repository.getPITMovies(apiKey = API_KEY)
    }
}

class GetTrendingMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Flow<Resource<Movies>> {
        return repository.getTrendingMovies(apiKey = API_KEY)
    }
}

class GetMoveGenresUseCase @Inject constructor(private val repository: Repository){
    suspend fun execute():Flow<Resource<Genre>>{
        return repository.getMovieGenres(API_KEY)
    }
}

class GetSearchMoviesUseCase @Inject constructor(private val repository: Repository){
    suspend fun execute():Flow<Resource<Movies>>{
        return repository.searchMovie()
    }
}
