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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: GetSearchMoviesUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val searchTvShowsUseCase: SearchTvShowsUseCase,
    private val searchAllItemsUseCase: SearchAllItemsUseCase

) :ViewModel() {

    private val debounceDelay = 400L

    private val _movies = MutableStateFlow<Resource<Movies>?>(null)
    val movies: MutableStateFlow<Resource<Movies>?> = _movies

    private val _tvShows = MutableStateFlow<Resource<TvShows>?>(null)
    val tvShows: MutableStateFlow<Resource<TvShows>?> = _tvShows

    private var _people  = MutableStateFlow<Resource<Person>?>(null)
    val people : MutableStateFlow<Resource<Person>?> = _people

    private var _searchNameList = MutableStateFlow<MutableList<String>>(mutableListOf())
    val searchNameList :MutableStateFlow<MutableList<String>> = _searchNameList


    private val _textFlow = MutableStateFlow("")
    val textFlow: MutableStateFlow<String> = _textFlow

    private var debounceJob: Job? = null

    fun setTextFlow(txt:String){
        _textFlow.value  = txt
    }

    fun cancelDebounceJob(){
        debounceJob?.cancel()
    }

    fun clearSearch(){
        _textFlow.value = ""
        searchNameList.value.clear()
    }

    @OptIn(FlowPreview::class)
    fun startDebounceJob() {
        debounceJob = viewModelScope.launch {
            textFlow.debounce(debounceDelay).collect {
                getAllItemInfo(it)
                println("hehehehe")
            }

        }
    }

    fun getAllItemInfo(query: String) {
        viewModelScope.launch {
            val localLst = mutableListOf<String>()
            searchAllItemsUseCase.execute(query).collect{
                it.data?.let {
                    println(it)
                    it.results?.let {
                        it.map {
                            it?.let {
                                localLst.add(it.title?:it.originalTitle?:it.name ?:it.originalName?:"")
                            }
                        }
                        searchNameList.value = localLst
                        println(searchNameList)
                    }
                }

            }
        }
    }

    fun getEverythingByQuery(query:String){
        searchMovie(query)
        searchTv(query)
        searchPerson(query)
    }

    private fun searchPerson(query: String) {
        viewModelScope.launch {
            searchPeopleUseCase.execute(query = query).collect{

                _people.value = it
            }
        }
    }

    private fun searchTv(query: String) {
        viewModelScope.launch {
            searchTvShowsUseCase.execute(query = query).collect{

                _tvShows.value = it
            }
        }
    }

    private fun searchMovie(query: String) {
        viewModelScope.launch {
            searchMoviesUseCase.execute(query = query).collect{

                _movies.value = it
            }
        }
    }


}