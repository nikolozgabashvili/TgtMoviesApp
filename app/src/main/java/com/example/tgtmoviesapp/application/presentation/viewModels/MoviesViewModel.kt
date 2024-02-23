package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Status
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import com.example.tgtmoviesapp.application.domain.usecases.GetPopularMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTopRatedMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetPopularMoviesUseCase,
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _popularMoviesSuccess = MutableStateFlow<PopularMovies?>(null)
    val popularMoviesSuccess: MutableStateFlow<PopularMovies?> = _popularMoviesSuccess

    private val _popularMoviesLoading = MutableStateFlow(false)
    val popularMoviesLoading: MutableStateFlow<Boolean> = _popularMoviesLoading

    private val _popularMoviesError = MutableStateFlow("")
    val popularMoviesError: MutableStateFlow<String> = _popularMoviesError

    private val _upcomingMoviesSuccess = MutableStateFlow<UpcomingMovies?>(null)
    val upcomingMoviesSuccess: MutableStateFlow<UpcomingMovies?> = _upcomingMoviesSuccess

    private val _upcomingMoviesLoading = MutableStateFlow(false)
    val upcomingMoviesLoading: MutableStateFlow<Boolean> = _upcomingMoviesLoading

    private val _upcomingMoviesError = MutableStateFlow("")
    val upcomingMoviesError: MutableStateFlow<String> = _upcomingMoviesError

    private val _topRatedMoviesSuccess = MutableStateFlow<PopularMovies?>(null)
    val topRatedMoviesSuccess: MutableStateFlow<PopularMovies?> = _topRatedMoviesSuccess

    private val _topRatedMoviesLoading = MutableStateFlow(false)
    val topRatedMoviesLoading: MutableStateFlow<Boolean> = _topRatedMoviesLoading

    private val _topRatedMoviesError = MutableStateFlow("")
    val topRatedMoviesError: MutableStateFlow<String> = _topRatedMoviesError


    init {
        getPopularMovies()
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getUpcomingMovies() {
        _upcomingMoviesLoading.value = true

        viewModelScope.launch {
            upcomingMoviesUseCase.execute().collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {

                            _upcomingMoviesSuccess.value = it
                            _upcomingMoviesLoading.value = false

                        }
                    }

                    Status.ERROR -> {
                        println("status error returned")
                        _upcomingMoviesError.value = resource.message.toString()
                        _upcomingMoviesLoading.value = false
                    }

                    Status.LOADING -> _upcomingMoviesLoading.value = true

                }


            }

        }
    }

    private fun getPopularMovies() {

        _popularMoviesLoading.value = true

        viewModelScope.launch {
            moviesUseCase.execute().collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {

                            _popularMoviesSuccess.value = it
                            _popularMoviesLoading.value = false

                        }
                    }

                    Status.ERROR -> {
                        println("status error returned")
                        _popularMoviesError.value = resource.message.toString()
                        _popularMoviesLoading.value = false
                    }

                    Status.LOADING -> _popularMoviesLoading.value = true

                }


            }

        }


    }

    private fun getTopRatedMovies(){


        _topRatedMoviesLoading.value = true

        viewModelScope.launch {
            topRatedMoviesUseCase.execute().collect { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {

                            _topRatedMoviesSuccess.value = it
                            _topRatedMoviesLoading.value = false

                        }
                    }

                    Status.ERROR -> {
                        println("status error returned")
                        _topRatedMoviesError.value = resource.message.toString()
                        _topRatedMoviesLoading.value = false
                    }

                    Status.LOADING -> _topRatedMoviesLoading.value = true

                }


            }

        }


    }


}