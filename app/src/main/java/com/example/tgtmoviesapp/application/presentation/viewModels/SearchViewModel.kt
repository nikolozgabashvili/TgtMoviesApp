package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.domain.usecases.GetSearchMoviesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.SearchAllItemsUseCase
import com.example.tgtmoviesapp.application.domain.usecases.SearchPeopleUseCase
import com.example.tgtmoviesapp.application.domain.usecases.SearchTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val searchTvShowsUseCase: SearchTvShowsUseCase,
    private val searchAllItemsUseCase: SearchAllItemsUseCase

) : ViewModel() {

    private val debounceDelay = 400L

    private val _movies = MutableStateFlow<Resource<Movies>?>(null)
    val movies: MutableStateFlow<Resource<Movies>?> = _movies

    private val _moviesPaged = MutableStateFlow<Resource<Movies>?>(null)
    val moviesPaged: MutableStateFlow<Resource<Movies>?> = _moviesPaged

    private val _tvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val tvShows: MutableStateFlow<Resource<TvShows>?> = _tvShows

    private val _tvShowsPaged = MutableStateFlow<Resource<TvShows>?>(null)
    val tvShowsPaged: MutableStateFlow<Resource<TvShows>?> = _tvShowsPaged


    private var _people = MutableStateFlow<Resource<Person>?>(null)
    val people: MutableStateFlow<Resource<Person>?> = _people

    private var _peoplePaged = MutableStateFlow<Resource<Person>?>(null)
    val peoplePaged: MutableStateFlow<Resource<Person>?> = _peoplePaged

    private var _searchNameList = MutableSharedFlow<MutableList<String>>()
    val searchNameList = _searchNameList.asSharedFlow()

    var foundList = MutableStateFlow<Array<List<Any?>>>(Array(3) { emptyList() })


    private val _textFlow = MutableStateFlow("")
    val textFlow: MutableStateFlow<String> = _textFlow

    private var debounceJob: Job? = null

    fun setTextFlow(txt: String) {
        _textFlow.value = txt
    }

    fun cancelDebounceJob() {
        debounceJob?.cancel()
    }

    fun clearFoundList() {
        foundList.value[0] = emptyList()
        foundList.value[1] = emptyList()
        foundList.value[2] = emptyList()
    }

//    fun clearSearch() {
//        _textFlow.value = ""
//        _searchNameList.clear()
//    }

    @OptIn(FlowPreview::class)
    fun startDebounceJob() {
        debounceJob = viewModelScope.launch {
            textFlow.debounce(0).collect {
                getAllItemInfo(it)
            }

        }
    }


    private fun getAllItemInfo(query: String) {
        viewModelScope.launch {
            val localLst = mutableListOf<String>()
            searchAllItemsUseCase.execute(query).collect {
                it.data?.let {
                    it.results?.let {
                        it.map {
                            it?.let {
                                localLst.add(
                                    it.title ?: it.originalTitle ?: it.name ?: it.originalName ?: ""
                                )
                            }
                        }
                        _searchNameList.emit(localLst)

                    }
                }

            }
        }
    }

    fun getEverythingByQuery(query: String) {
        searchMovie(query)
        searchTv(query)
        searchPerson(query)
    }

    private fun searchPerson(query: String) {
        viewModelScope.launch {
            searchPeopleUseCase.execute(query = query).collect {

                _people.value = it
                _peoplePaged.value = it
            }
        }
    }

    private fun searchTv(query: String) {
        viewModelScope.launch {
            searchTvShowsUseCase.execute(query = query).collect {

                _tvShows.value = it
                _tvShowsPaged.value = it
            }
        }
    }

    fun searchMovie(query: String, page: Int = 1) {
        viewModelScope.launch {
            searchMoviesUseCase.execute(query = query, page).collect {

                _movies.value = it
                _moviesPaged.value = it
            }
        }
    }

    fun searchMovieByPage(query: String, page: Int = 1) {
        viewModelScope.launch {
            searchMoviesUseCase.execute(query = query, page).collect {

                _moviesPaged.value = it

            }
        }
    }

    fun searchTvShowsByPage(query: String, page: Int = 1) {
        viewModelScope.launch {
            searchTvShowsUseCase.execute(query = query, page).collect {

                _tvShowsPaged.value = it
            }
        }
    }

    fun searchPeopleByPage(query: String, page: Int = 1) {
        viewModelScope.launch {
            searchPeopleUseCase.execute(query = query, page).collect {

                _peoplePaged.value = it
            }
        }
    }


}