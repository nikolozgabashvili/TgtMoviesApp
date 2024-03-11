package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.usecases.LoginUserUseCase
import com.example.tgtmoviesapp.application.domain.usecases.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _registerResource = MutableStateFlow<Resource<String>?>(null)
    val registerResource: MutableStateFlow<Resource<String>?> = _registerResource

    private val _loginResource = MutableStateFlow<Resource<String>?>(null)
    val loginResource: MutableStateFlow<Resource<String>?> = _loginResource

    var currentUserToken = MutableStateFlow<String>("")


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
                if (it.data!=null)
                    currentUserToken.value = it.data
                _loginResource.value = it

            }

        }


    }


}