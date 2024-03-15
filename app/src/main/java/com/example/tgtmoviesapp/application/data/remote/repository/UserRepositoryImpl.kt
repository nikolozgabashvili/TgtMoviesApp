package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.modelsDto.LoginClass
import com.example.tgtmoviesapp.application.data.modelsDto.RegisterClass
import com.example.tgtmoviesapp.application.data.remote.UserServices
import com.example.tgtmoviesapp.application.data.remote.mappers.toCurrentUserModel
import com.example.tgtmoviesapp.application.data.remote.mappers.toMovieId
import com.example.tgtmoviesapp.application.data.remote.mappers.toRegisterResponse
import com.example.tgtmoviesapp.application.data.remote.mappers.toStringList
import com.example.tgtmoviesapp.application.domain.models.CurrentUserModel
import com.example.tgtmoviesapp.application.domain.models.FavMovieId
import com.example.tgtmoviesapp.application.domain.models.MovieAdd
import com.example.tgtmoviesapp.application.domain.models.RegisterError
import com.example.tgtmoviesapp.application.domain.models.RegisterResponse
import com.example.tgtmoviesapp.application.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(private val userApi: UserServices) : UserRepository {
    override suspend fun registerUser(
        username: String,
        password: String,
        mail: String
    ): Flow<Resource<RegisterResponse>> = flow {
        try {
            emit(Resource.Loading(loading = true))
            val response = userApi.registerUser(RegisterClass(username, password, mail))
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toRegisterResponse()))
            } else {

                val gson = Gson()
                val errorResponse: RegisterError =
                    gson.fromJson(response.errorBody()?.charStream(), RegisterError::class.java)
                if (errorResponse.errors != null)
                    emit(Resource.Error((errorResponse.errors)))
                else
                    emit(Resource.Error(errorResponse.message.toString().toStringList()))

            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString().toStringList()))

        }
    }

    override suspend fun loginUser(username: String, password: String): Flow<Resource<String>> =
        flow {
            try {

                emit(Resource.Loading(loading = true))
                val response = userApi.loginUser(LoginClass(username, password))
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()))
                } else {
                    val gson = Gson()
                    val errorResponse: RegisterError =
                        gson.fromJson(response.errorBody()?.charStream(), RegisterError::class.java)
                    emit(Resource.Error(errorResponse.message.toString().toStringList()))


                }
            } catch (e: Exception) {
                emit(Resource.Error(e.toString().toStringList()))

            }
        }

    override suspend fun getCurrentUser(bearer: String): Flow<Resource<CurrentUserModel>> = flow {
        try {

            emit(Resource.Loading(loading = true))
            val response = userApi.getCurrentUser("bearer $bearer")
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toCurrentUserModel()))
            } else {
                if (response.code() == 401) {
                    emit(Resource.Error("session ended".toStringList()))
                } else {
                    val gson = Gson()
                    val errorResponse: RegisterError =
                        gson.fromJson(response.errorBody()?.charStream(), RegisterError::class.java)
                    emit(Resource.Error(errorResponse.message.toString().toStringList()))
                }


            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString().toStringList()))

        }
    }

    override suspend fun addFavouriteMovie(bearer: String, id: Int): Flow<Resource<FavMovieId>> = flow {
        try {

            emit(Resource.Loading(loading = true))
            val response = userApi.addFavourite("bearer $bearer", MovieAdd(id))
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.toMovieId()))
            } else {
                if (response.code() == 401) {
                    emit(Resource.Error("session ended".toStringList()))
                } else {
                    val gson = Gson()
                    val errorResponse: RegisterError =
                        gson.fromJson(response.errorBody()?.charStream(), RegisterError::class.java)
                    emit(Resource.Error(errorResponse.message.toString().toStringList()))
                }

            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString().toStringList()))

        }
    }

    override suspend fun isFavourite(bearer: String,id:Int): Flow<Resource<Boolean>> = flow {
        try {

            emit(Resource.Loading(loading = true))
            val response = userApi.isFavourite("bearer $bearer", id)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()?.isAdded))
            } else {
                if (response.code() == 401) {
                    emit(Resource.Error("session ended".toStringList()))
                } else {
                    val gson = Gson()
                    val errorResponse: RegisterError =
                        gson.fromJson(response.errorBody()?.charStream(), RegisterError::class.java)
                    emit(Resource.Error(errorResponse.message.toString().toStringList()))
                }

            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString().toStringList()))

        }
    }

}