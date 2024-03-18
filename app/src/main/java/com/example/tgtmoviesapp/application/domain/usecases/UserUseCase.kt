package com.example.tgtmoviesapp.application.domain.usecases

import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.CurrentUserModel
import com.example.tgtmoviesapp.application.domain.models.FavMovieId
import com.example.tgtmoviesapp.application.domain.models.RegisterResponse
import com.example.tgtmoviesapp.application.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend fun execute(username:String,password:String,mail:String):Flow<Resource<RegisterResponse>>{
        return repository.registerUser(username,password, mail)
    }
}
class LoginUserUseCase @Inject constructor(private val repository: UserRepository){
    suspend fun execute(username:String,password: String):Flow<Resource<String>>{
        return repository.loginUser(username,password)
    }
}
class GetCurrentUserUseCase @Inject constructor(private val repository: UserRepository){
    suspend fun execute(bearer:String):Flow<Resource<CurrentUserModel>>{
        return repository.getCurrentUser(bearer)
    }
}
class AddFavouritesUseCase @Inject constructor(private val repository: UserRepository){
    suspend fun execute(bearer: String,id:Int):Flow<Resource<FavMovieId>>{
        return repository.addFavouriteMovie(bearer,id)
    }
}

class CheckIsFavourite @Inject constructor(private val repository: UserRepository){
    suspend fun execute(bearer: String,id:Int):Flow<Resource<Boolean>>{
        return repository.isFavourite(bearer,id)
    }
}

class DeleteFavouriteUseCase @Inject constructor(private val repository: UserRepository){

    suspend fun execute(bearer: String,id: Int):Flow<Resource<Boolean>>{
        return repository.deleteFavourite(bearer,id)
    }
}