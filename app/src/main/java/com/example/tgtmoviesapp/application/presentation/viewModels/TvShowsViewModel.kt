package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.usecases.GetPopularTvShowsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTopRatedTvShowsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTrendingTvShowsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTvGenresUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetUpcomingTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTrendingTvShowsUseCase: GetTrendingTvShowsUseCase,
    private val getUpcomingTvShowsUseCase: GetUpcomingTvShowsUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val getTvGenresUseCase: GetTvGenresUseCase
) : ViewModel() {

    private val _popularTvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val popularTvShows: MutableStateFlow<Resource<TvShows>?> = _popularTvShows

    private val _upcomingTvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val upcomingTvShows: MutableStateFlow<Resource<TvShows>?> = _upcomingTvShows

    private val _trendingTvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val trendingTvShows: MutableStateFlow<Resource<TvShows>?> = _trendingTvShows

    private val _topRatedTvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val topRatedTvShows: MutableStateFlow<Resource<TvShows>?> = _topRatedTvShows

    private val _tvGenres = MutableStateFlow<Resource<TvGenre>?>(null)
    val tvGenres: MutableStateFlow<Resource<TvGenre>?> = _tvGenres


    init {
        getTvGenres()
        getPopularMovies()
        getUpcomingMovies()
        getTopRatedMovies()
        getPITMovies()
        getTrendingMovies()
    }

    private fun getTvGenres() {
        viewModelScope.launch {
            getTvGenresUseCase.execute().collect{resource->
                _tvGenres.value = resource
            }
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            getTopRatedTvShowsUseCase.execute().collect { resource ->
                _topRatedTvShows.value = resource
            }
        }
    }

    private fun getPITMovies() {
        viewModelScope.launch {
            getTopRatedTvShowsUseCase.execute().collect { resource ->
                _topRatedTvShows.value = resource
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            getUpcomingTvShowsUseCase.execute().collect { resource ->
                _upcomingTvShows.value = resource
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            getTrendingTvShowsUseCase.execute().collect { resource ->
                _trendingTvShows.value = resource
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularTvShowsUseCase.execute().collect { resource ->
                _popularTvShows.value = resource
            }
        }
    }

}