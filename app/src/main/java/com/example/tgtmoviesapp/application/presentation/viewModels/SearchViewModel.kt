package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.usecases.GetSearchMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.SearchPeopleUseCase
import com.example.tgtmoviesapp.application.domain.usecases.SearchTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val searchTvShowsUseCase: SearchTvShowsUseCase

) :ViewModel() {

    private val _movies = MutableStateFlow<Resource<Movies>?>(null)
    val movies: MutableStateFlow<Resource<Movies>?> = _movies

    private val _tvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val tvShows: MutableStateFlow<Resource<TvShows>?> = _tvShows

    private var _people  = MutableStateFlow<Resource<Person>?>(null)
    val people : MutableStateFlow<Resource<Person>?> = _people






}