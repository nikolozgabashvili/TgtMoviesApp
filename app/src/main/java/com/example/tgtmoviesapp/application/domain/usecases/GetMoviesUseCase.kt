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
import javax.inject.Qualifier

class GetPopularMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Movies>> {
        return repository.getPopularMovies(API_KEY, page = page)
    }

}

class GetUpcomingMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Movies>> {
        return repository.getUpcomingMovies(API_KEY, page = page)
    }

}



class GetTopRatedMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Movies>> {
        return repository.getTopRatedMovies(apiKey = API_KEY, page = page)
    }

}
class GetPITMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Movies>> {
        return repository.getPITMovies(apiKey = API_KEY, page = page)
    }
}

class GetTrendingMoviesUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(page:Int = 1): Flow<Resource<Movies>> {
        return repository.getTrendingMovies(apiKey = API_KEY, page = page)
    }
}

class GetMoveGenresUseCase @Inject constructor(private val repository: Repository){
    suspend fun execute():Flow<Resource<Genre>>{
        return repository.getMovieGenres(API_KEY)
    }
}

class GetSearchMoviesUseCase @Inject constructor(private val repository: Repository){
    suspend fun execute(query:String,page:Int=1):Flow<Resource<Movies>>{
        return repository.searchMovie(query = query, page = page)
    }
}

class SearchAllItemsUseCase @Inject constructor(private val repository: Repository){
    suspend fun execute(query:String):Flow<Resource<AllItemModel>>{
        return repository.getSearchResults(query)
    }
}
