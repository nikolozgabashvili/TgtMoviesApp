package com.example.tgtmoviesapp.application.presentation.viewModels

import android.util.StringBuilderPrinter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.usecases.GetDetailsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetMovieCastUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetMovieVideosUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetRecommendedMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetSimilarMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetDetailsUseCase,
    private val similarMoviesUseCase: GetSimilarMoviesUseCase,
    private val recommendedMoviesUseCase: GetRecommendedMoviesUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase
) :
    ViewModel() {

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>?>(null)
    val movieDetails: MutableStateFlow<Resource<MovieDetails>?> = _movieDetails


    private val _similarMovies = MutableStateFlow<Resource<Movies>?>(null)
    val similarMovies: MutableStateFlow<Resource<Movies>?> = _similarMovies

    private val _recommended = MutableStateFlow<Resource<Movies>?>(null)
    val recommended: MutableStateFlow<Resource<Movies>?> = _recommended

    private val _videos = MutableStateFlow<Resource<MovieVideo>?>(null)
    val videos: MutableStateFlow<Resource<MovieVideo>?> = _videos

    private val _cast = MutableStateFlow<Resource<Person>?>(null)
    val cast: MutableStateFlow<Resource<Person>?> = _cast




    fun getMovieDetails(id: Int) {

        viewModelScope.launch {

            detailsUseCase.execute(id).collect { resource ->

                _movieDetails.value = resource



            }

        }

    }

    fun getSimilarMovies(id:Int,page:Int = 1){
        viewModelScope.launch {
            similarMoviesUseCase.execute(id,page).collect{
                _similarMovies.value = it


            }
        }
    }

    fun getRecommendedMovies(id:Int,page:Int = 1){
        viewModelScope.launch {
            recommendedMoviesUseCase.execute(id,page).collect{

                _recommended.value = it


            }
        }
    }

    fun getMovieVideos(id:Int){
        viewModelScope.launch {
            getMovieVideosUseCase.execute(id).collect{
                _videos.value  = it
                println(it)
            }
        }
    }
    fun  getCast(id:Int){
        viewModelScope.launch {
            getMovieCastUseCase.execute(id).collect{
                _cast.value = it
                println("cast-------------------------------------------" +
                        "-------------------------------------------------" +
                        "${it.data}")
                println("cast-------------------------------------------" +
                        "-------------------------------------------------" +
                        "${it.message}")
            }
        }
    }


}