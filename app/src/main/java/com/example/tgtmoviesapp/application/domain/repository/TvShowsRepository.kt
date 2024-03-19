package com.example.tgtmoviesapp.application.domain.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    suspend fun getPopularTvShows(apiKey:String?,page:Int = 1): Flow<Resource<TvShows>>
    suspend fun getTopRatedTvShows(apiKey:String?,page:Int = 1): Flow<Resource<TvShows>>
    suspend fun getUpcomingTvShows(apiKey:String?,page:Int = 1): Flow<Resource<TvShows>>
    suspend fun getTrendingTvShows(apiKey:String?,page:Int = 1): Flow<Resource<TvShows>>

    //------------
    suspend fun getTvGenres(apiKey: String?):Flow<Resource<TvGenre>>

    suspend fun searchTvShows(query: String ,page:Int):Flow<Resource<TvShows>>

    suspend fun getTvDetails(id:Int):Flow<Resource<MovieDetails>>
    suspend fun getSimilarShows(id:Int,page:Int = 1):Flow<Resource<TvShows>>
    suspend fun getRecommendedShows(id:Int,page:Int = 1):Flow<Resource<TvShows>>
    suspend fun getTvVideos(id:Int):Flow<Resource<MovieVideo>>
    suspend fun getTvCast(id:Int):Flow<Resource<Person>>

    suspend fun getShowByPersonId(id:Int):Flow<Resource<TvShows>>


}