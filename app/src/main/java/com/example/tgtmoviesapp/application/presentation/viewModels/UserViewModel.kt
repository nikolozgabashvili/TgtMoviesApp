package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.MySharedPreferences
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.RegisterResponse
import com.example.tgtmoviesapp.application.domain.usecases.GetCurrentUserUseCase
import com.example.tgtmoviesapp.application.domain.usecases.LoginUserUseCase
import com.example.tgtmoviesapp.application.domain.usecases.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sharedPreferences: MySharedPreferences
) : ViewModel() {

    private val _registerResource = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val registerResource: MutableStateFlow<Resource<RegisterResponse>?> = _registerResource

    private val _loginResource = MutableStateFlow<Resource<String>?>(null)
    val loginResource: MutableStateFlow<Resource<String>?> = _loginResource


    var currentUserToken = MutableStateFlow<String?>(null)
    var currentUserName = MutableStateFlow<String?>(null)

    fun registerUser(username: String, password: String, mail: String) {
        viewModelScope.launch {
            userUseCase.execute(username, password, mail).collect {
                _registerResource.value = it
            }

        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase.execute(username, password).collect {
                it.data?.let { str ->
                    currentUserToken.value = str
                    sharedPreferences.putString("userToken", str)
                }
            }

        }

    }

    init {
        sharedPreferences.getString("userToken", null)?.let {
            currentUserToken.value = it

        }
        viewModelScope.launch {
            currentUserToken.collect {
                sharedPreferences.putString("userToken", it)
                it?.let {
                    viewModelScope.launch {
                        getCurrentUserUseCase.execute(it).collect { user ->
                            user.data?.let {
                                currentUserName.value = it.username
                            }
                        }
                    }
                }
            }
        }

    }

    fun removeTokenValue() {
        currentUserToken.value = null
        currentUserName.value = null
    }


}