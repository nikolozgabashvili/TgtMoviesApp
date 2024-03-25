package com.example.tgtmoviesapp.application.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tgtmoviesapp.application.commons.MySharedPreferences
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.RegisterResponse
import com.example.tgtmoviesapp.application.domain.usecases.AddFavouritesUseCase
import com.example.tgtmoviesapp.application.domain.usecases.CheckIsFavourite
import com.example.tgtmoviesapp.application.domain.usecases.DeleteFavouriteUseCase
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
    private val sharedPreferences: MySharedPreferences,
    private val checkIsFavourite: CheckIsFavourite,
    private val addFavouritesUseCase: AddFavouritesUseCase,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCase
) : ViewModel() {

    private val _registerResource = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val registerResource: MutableStateFlow<Resource<RegisterResponse>?> = _registerResource

    private val _loginResource = MutableStateFlow<Resource<String>?>(null)
    val loginResource: MutableStateFlow<Resource<String>?> = _loginResource

    private val _isFavourite = MutableStateFlow<Resource<Boolean>?>(null)
    val isFavourite: MutableStateFlow<Resource<Boolean>?> = _isFavourite

    private val _favouriteSuccess = MutableStateFlow(false)
    val favouriteSuccess: MutableStateFlow<Boolean> = _favouriteSuccess

    private val _deleteSuccess = MutableStateFlow<Resource<Boolean>?>(null)
    val deleteSuccess: MutableStateFlow<Resource<Boolean>?> = _deleteSuccess

    var currentUserToken = MutableStateFlow<String?>(null)
    var checkToken = MutableStateFlow(false)
    var currentUserName = MutableStateFlow<String?>(null)


    var errorMsg = ""

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
                    checkToken.value = true
                    sharedPreferences.putString("userToken", str)

                }

                _loginResource.value = it
            }

        }

    }

    fun addFavourite(id: Int) {
        viewModelScope.launch {
            addFavouritesUseCase.execute(currentUserToken.value!!, id).collect {
                if (it.data != null) {
                    _favouriteSuccess.value = true
                    isFavourite.value = Resource.Success(true)
                } else if (it.message != null) {
                    _favouriteSuccess.value = false
                }
                it.message?.let {
                    it.map {
                        if (it == "401") {
                            errorMsg = "401"
                            removeTokenValue()
                        }
                    }
                }
            }
        }
    }

    fun deleteFav(id: Int) {
        viewModelScope.launch {
            currentUserToken.value?.let {
                deleteFavouriteUseCase.execute(it, id).collect {
                    _deleteSuccess.value = it
                    if (it.data == true) {
                        isFavourite.value = Resource.Success(false)
                    }
                    it.message?.let {
                        it.map {
                            if (it == "401") {
                                errorMsg = "401"
                                removeTokenValue()
                            }
                        }
                    }
                }

            }
        }
    }

    fun isMovieFavourite(id: Int) {
        viewModelScope.launch {
            currentUserToken.value?.let {
                checkIsFavourite.execute(it, id).collect {
                    it?.let {
                        _isFavourite.emit(it)
                    }
                    it.message?.let {
                        it.map {
                            if (it == "401") {

                                removeTokenValue()
                            }
                        }
                    }
                    if (it.data != null) {
                        checkToken.value = true
                    }
                }

            }
        }
    }

    init {
        viewModelScope.launch {

            sharedPreferences.getString("userToken", null)?.let {
                currentUserToken.value = it
                isMovieFavourite(-1)


            }

        }



        viewModelScope.launch {
            currentUserToken.collect {
                it?.let {

                    sharedPreferences.putString("userToken", it)
                }
                it?.let {
                    viewModelScope.launch {
                        getCurrentUserUseCase.execute(it).collect { user ->
                            user.data?.let {
                                currentUserName.value = it.username
                            }
                            user.message?.let {
                                it.map {
                                    if (it == "401") {
                                        errorMsg = "401"
                                        removeTokenValue()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    fun removeTokenValue() {
        currentUserToken.value = null
        checkToken.value = false
        currentUserName.value = null
        sharedPreferences.clearPrefs()
    }


}