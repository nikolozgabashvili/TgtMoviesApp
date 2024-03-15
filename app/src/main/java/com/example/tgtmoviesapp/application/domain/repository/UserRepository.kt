package com.example.tgtmoviesapp.application.domain.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.CurrentUserModel
import com.example.tgtmoviesapp.application.domain.models.FavMovieId
import com.example.tgtmoviesapp.application.domain.models.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(username:String,password:String,mail:String): Flow<Resource<RegisterResponse>>
    suspend fun loginUser(username:String,password:String): Flow<Resource<String>>
    suspend fun getCurrentUser(bearer:String): Flow<Resource<CurrentUserModel>>
    suspend fun addFavouriteMovie(bearer:String,id:Int): Flow<Resource<FavMovieId>>
    suspend fun isFavourite(bearer:String,id:Int): Flow<Resource<Boolean>>


}