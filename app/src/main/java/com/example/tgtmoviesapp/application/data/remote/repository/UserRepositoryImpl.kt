package com.example.tgtmoviesapp.application.data.remote.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.data.modelsDto.LoginClass
import com.example.tgtmoviesapp.application.data.remote.UserServices

import com.example.tgtmoviesapp.application.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userApi: UserServices) : UserRepository {
    override suspend fun registerUser(username: String, password: String, mail: String): Flow<Resource<String>> = flow {
        try {
            val response = userApi.registerUser(username, password, mail)
            emit(Resource.Loading(loading = true))
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error("error", null))

            }
        } catch (e: Exception) {
            emit(Resource.Error("error"))

        }
    }

    override suspend fun loginUser(username: String, password: String): Flow<Resource<String>> = flow {
        try {


            val response = userApi.loginUser(LoginClass(username,password))
            emit(Resource.Loading(loading = true))
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error(response.message(), null))

            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))

        }
    }

}