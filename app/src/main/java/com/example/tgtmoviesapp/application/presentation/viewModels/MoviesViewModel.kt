package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.usecases.GetMoveGenresUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetPITMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetPopularMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTopRatedMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTrendingMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetPopularMoviesUseCase,
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val pITMoviesUseCase: GetPITMoviesUseCase,
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val moveGenresUseCase: GetMoveGenresUseCase

    ) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<Movies>?>(null)
    val movies: MutableStateFlow<Resource<Movies>?> = _movies


    private val _upcomingMovies = MutableStateFlow<Resource<Movies>?>(null)
    val upcomingMovies: MutableStateFlow<Resource<Movies>?> = _upcomingMovies


    private val _topRatedMovies = MutableStateFlow<Resource<Movies>?>(null)
    val topRatedMovies: MutableStateFlow<Resource<Movies>?> = _topRatedMovies


    private val _pITMovies = MutableStateFlow<Resource<Movies>?>(null)
    val pITMovies: MutableStateFlow<Resource<Movies>?> = _pITMovies

    private val _trendingMovies = MutableStateFlow<Resource<Movies>?>(null)
    val trendingMovies: MutableStateFlow<Resource<Movies>?> = _trendingMovies

    private val _movieGenres = MutableStateFlow<Resource<Genre>?>(null)
    val moviesGenres: MutableStateFlow<Resource<Genre>?> = _movieGenres

    init {
        println("init")
        getMovieGenres()
        getPopularMovies()
        getUpcomingMovies()
        getTopRatedMovies()
        getPITMovies()
        getTrendingMovies()

    }
    fun activate(){

    }

    private fun getMovieGenres() {
        viewModelScope.launch {
            moveGenresUseCase.execute().collect { resource ->

                _movieGenres.value = resource


            }

        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            trendingMoviesUseCase.execute().collect { resource ->

                _trendingMovies.value = resource



            }

        }
    }

    private fun getPITMovies() {


        viewModelScope.launch {
            pITMoviesUseCase.execute().collect { resource ->

                _pITMovies.value = resource


            }

        }

    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            upcomingMoviesUseCase.execute().collect { resource ->

                _upcomingMovies.value = resource


            }

        }
    }

    private fun getPopularMovies() {

        viewModelScope.launch {
            moviesUseCase.execute().collect { resource ->

                _movies.value = resource


            }

        }

    }

    private fun getTopRatedMovies() {


        viewModelScope.launch {
            topRatedMoviesUseCase.execute().collect { resource ->

                _topRatedMovies.value = resource


            }

        }


    }


}