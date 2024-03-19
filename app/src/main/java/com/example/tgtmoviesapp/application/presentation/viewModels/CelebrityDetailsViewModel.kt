package com.example.tgtmoviesapp.application.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.PersonDetails
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.usecases.GetPeopleDetailsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetPersonMovieCreditsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetPersonTvShowCreditsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CelebrityDetailsViewModel @Inject constructor(
    private val getPeopleDetailsUseCase: GetPeopleDetailsUseCase,
    private val getPersonMovieCreditsUseCase: GetPersonMovieCreditsUseCase,
    private val getPersonTvShowCreditsUseCase: GetPersonTvShowCreditsUseCase

) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<Movies>?>(null)
    val movies: MutableStateFlow<Resource<Movies>?> = _movies


    private val _tvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val tvShows: MutableStateFlow<Resource<TvShows>?> = _tvShows

    private val _peopleDetails = MutableStateFlow<Resource<PersonDetails>?>(null)
    val peopleDetails: MutableStateFlow<Resource<PersonDetails>?> = _peopleDetails


    fun getPersonDetails(id: Int) {

        viewModelScope.launch {

            getPeopleDetailsUseCase.execute(id).collect { resource ->
                _peopleDetails.value = resource

            }
        }
    }
    fun getTvShows(id: Int) {

        viewModelScope.launch {

            getPersonTvShowCreditsUseCase.execute(id).collect { resource ->

                _tvShows.value = resource
                println("-------------")
                println(resource.data)
                println(resource.message)
                println("-------------")
            }
        }
    }
    fun getMovies(id: Int) {
        viewModelScope.launch {

            getPersonMovieCreditsUseCase.execute(id).collect { resource ->
                println("-------------")
                println(resource.data)
                println(resource.message)
                println("-------------")
                _movies.value = resource

            }
        }
    }
}