package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend fun execute(username:String,password:String,mail:String):Flow<Resource<String>>{
        return repository.registerUser(username,password, mail)
    }
}
class LoginUserUseCase @Inject constructor(private val repository: UserRepository){
    suspend fun execute(username:String,password: String):Flow<Resource<String>>{
        return repository.loginUser(username,password)
    }
}