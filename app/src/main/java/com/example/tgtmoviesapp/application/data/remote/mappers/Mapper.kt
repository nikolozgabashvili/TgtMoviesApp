package com.example.tgtmoviesapp.application.data.remote.mappers

import com.example.tgtmoviesapp.application.data.modelsDto.MoviesDto
import com.example.tgtmoviesapp.application.data.modelsDto.UpcomingMoviesDto
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import java.lang.Exception

fun MoviesDto.toMovies():Movies{
    return Movies(
        page = page,
        totalPages = totalPages,
        results = results?.toResult(),
        totalResults = totalResults

    )
}

fun MoviesDto.ResultDto.toResult():Movies.Result{
    return Movies.Result(
        adult, backdropPath, genreIds,
        id, originalLanguage, originalTitle,
        overview, popularity, posterPath, releaseDate,
        title, video, voteAverage, voteCount
    )
}

fun List<MoviesDto.ResultDto?>.toResult():List<Movies.Result>{
    val resultList = mutableListOf<Movies.Result>()
    for (i in this){
        if (i ==null){
            throw Exception("null found in resultDto list")
        }
        resultList.add(i.toResult())
    }
    return resultList
}

fun UpcomingMoviesDto.toUpcomingMovies(): UpcomingMovies {
    return UpcomingMovies(
        page = page,
        totalPages = totalPages,
        results = results?.toResult2(),
        totalResults = totalResults

    )
}

fun UpcomingMoviesDto.ResultDto2.toResult2():UpcomingMovies.Result{
    return UpcomingMovies.Result(
        adult, backdropPath, firstAirDate,
        genreIds, id, name, originCountry,
        originalLanguage, originalName, overview,
        popularity, posterPath, voteAverage, voteCount

    )




}

fun List<UpcomingMoviesDto.ResultDto2?>.toResult2():List<UpcomingMovies.Result>{
    val resultList = mutableListOf<UpcomingMovies.Result>()
    for (i in this){
        if (i ==null){
            throw Exception("null found in resultDto list")
        }
        resultList.add(i.toResult2())
    }
    return resultList
}