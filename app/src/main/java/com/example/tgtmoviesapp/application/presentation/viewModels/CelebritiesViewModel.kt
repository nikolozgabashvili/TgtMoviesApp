package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.usecases.GetPopularPeopleUseCase
import com.example.tgtmoviesapp.application.domain.usecases.GetTrendingPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CelebritiesViewModel @Inject constructor(
    private val popularPeopleUseCase: GetPopularPeopleUseCase,
    private val trendingPeopleUseCase: GetTrendingPeopleUseCase
) :ViewModel() {


    private var _popularPeople  = MutableStateFlow<Resource<Person>?>(null)
    val popularPeople : MutableStateFlow<Resource<Person>?> = _popularPeople

    private var _trendingPeople  = MutableStateFlow<Resource<Person>?>(null)
    val trendingPeople : MutableStateFlow<Resource<Person>?> = _trendingPeople

    init {
        getPopularPeople()
        getTrendingPeople()
    }

    private fun getTrendingPeople() {
        viewModelScope.launch {
            trendingPeopleUseCase.execute().collect { resource ->

                _trendingPeople.value = resource


            }

        }
    }

    private fun getPopularPeople() {
        viewModelScope.launch {
            popularPeopleUseCase.execute().collect { resource ->

                _popularPeople.value = resource


            }

        }
    }


}