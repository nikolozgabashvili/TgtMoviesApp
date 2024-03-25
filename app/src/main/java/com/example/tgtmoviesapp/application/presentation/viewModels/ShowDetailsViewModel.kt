package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.usecases.GetRecommendedTvUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetSimilarShowsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTvCastUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTvDetailsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTvVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getSimilarShowsUseCase: GetSimilarShowsUseCase,
    private val getRecommendedTvUseCase: GetRecommendedTvUseCase,
    private val getTvCastUseCase: GetTvCastUseCase,
    private val getTvVideosUseCase: GetTvVideosUseCase

) :
    ViewModel() {

    private val _tvDetails = MutableStateFlow<Resource<MovieDetails>?>(null)
    val tvDetails: MutableStateFlow<Resource<MovieDetails>?> = _tvDetails


    private val _similarShows = MutableStateFlow<Resource<TvShows>?>(null)
    val similarShows: MutableStateFlow<Resource<TvShows>?> = _similarShows

    private val _recommended = MutableStateFlow<Resource<TvShows>?>(null)
    val recommended: MutableStateFlow<Resource<TvShows>?> = _recommended

    private val _videos = MutableStateFlow<Resource<MovieVideo>?>(null)
    val videos: MutableStateFlow<Resource<MovieVideo>?> = _videos

    private val _cast = MutableStateFlow<Resource<Person>?>(null)
    val cast: MutableStateFlow<Resource<Person>?> = _cast



    fun getMovieDetails(id: Int) {

        viewModelScope.launch {

            getTvDetailsUseCase.execute(id).collect { resource ->

                _tvDetails.value = resource



            }

        }

    }

    fun getSimilarMovies(id:Int,page:Int = 1){
        viewModelScope.launch {
            getSimilarShowsUseCase.execute(id,page).collect{
                _similarShows.value = it


            }
        }
    }

    fun getRecommendedMovies(id:Int,page:Int = 1){
        viewModelScope.launch {
            getRecommendedTvUseCase.execute(id,page).collect{

                _recommended.value = it


            }
        }
    }

    fun getMovieVideos(id:Int){
        viewModelScope.launch {
            getTvVideosUseCase.execute(id).collect{
                _videos.value  = it
            }
        }
    }
    fun  getCast(id:Int){
        viewModelScope.launch {
            getTvCastUseCase.execute(id).collect{
                _cast.value = it

            }
        }
    }
}