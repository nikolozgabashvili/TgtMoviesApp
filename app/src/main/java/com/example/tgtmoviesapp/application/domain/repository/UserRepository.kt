package com.example.tgtmoviesapp.application.domain.repository

import com.example.tgtmoviesapp.application.commons.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(username:String,password:String,mail:String): Flow<Resource<String>>
    suspend fun loginUser(username:String,password:String): Flow<Resource<String>>

}