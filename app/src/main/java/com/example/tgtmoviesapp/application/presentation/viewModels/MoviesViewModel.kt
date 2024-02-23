package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Status
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.usecases.GetPopularMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetPopularMoviesUseCase,
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : ViewModel() {

    private val _popularMoviesSuccess = MutableStateFlow<PopularMovies?>(null)
    val popularMoviesSuccess: MutableStateFlow<PopularMovies?> = _popularMoviesSuccess

    private val _popularMoviesLoading = MutableStateFlow(false)
    val popularMoviesLoading: MutableStateFlow<Boolean> = _popularMoviesLoading

    private val _popularMoviesError = MutableStateFlow("")
    val popularMoviesError: MutableStateFlow<String> = _popularMoviesError

    private val _upcomingMoviesSuccess = MutableStateFlow<PopularMovies?>(null)
    val upcomingMoviesSuccess: MutableStateFlow<PopularMovies?> = _upcomingMoviesSuccess

    private val _upcomingMoviesLoading = MutableStateFlow(false)
    val upcomingMoviesLoading: MutableStateFlow<Boolean> = _upcomingMoviesLoading

    private val _upcomingMoviesError = MutableStateFlow("")
    val upcomingMoviesError: MutableStateFlow<String> = _upcomingMoviesError

    init {
        getPopularMovies()
        getUpcomingMovies()
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


}