package com.example.tgtmoviesapp.application.di

import com.example.tgtmoviesapp.application.commons.constants.Constants.AUTHENTICATION_URL
import com.example.tgtmoviesapp.application.commons.constants.Constants.BASE_URL
import com.example.tgtmoviesapp.application.data.remote.Api
import com.example.tgtmoviesapp.application.data.remote.UserServices
import com.example.tgtmoviesapp.application.data.remote.repository.CelebritiesRepositoryImpl
import com.example.tgtmoviesapp.application.data.remote.repository.RepositoryImpl
import com.example.tgtmoviesapp.application.data.remote.repository.TvShowsRepositoryImpl
import com.example.tgtmoviesapp.application.data.remote.repository.UserRepositoryImpl
import com.example.tgtmoviesapp.application.domain.repository.CelebritiesRepository
import com.example.tgtmoviesapp.application.domain.repository.Repository
import com.example.tgtmoviesapp.application.domain.repository.TvShowsRepository
import com.example.tgtmoviesapp.application.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {


    @Provides
    @Singleton
    @Named("movies")
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(@Named("movies") retrofit: Retrofit): Api {

        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository {
        return RepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTvRepository(api: Api): TvShowsRepository {
        return TvShowsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCelebRepository(api: Api): CelebritiesRepository {
        return CelebritiesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    @Named("userRetrofit")
    fun provideUserRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(AUTHENTICATION_URL)

            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(@Named("userRetrofit") userRetrofit: Retrofit): UserServices {
        return userRetrofit.create(UserServices::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserServices): UserRepository {
        return UserRepositoryImpl(userService)
    }


}