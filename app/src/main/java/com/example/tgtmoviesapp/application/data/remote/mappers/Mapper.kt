package com.example.tgtmoviesapp.application.data.remote.mappers

import com.example.tgtmoviesapp.application.data.modelsDto.MovieGenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.MoviesDto
import com.example.tgtmoviesapp.application.data.modelsDto.PersonDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvGenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvShowsDto
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows

fun MoviesDto.toMovies(): Movies {
    return Movies(
        page = page,
        totalPages = totalPages,
        results = results?.toResult(),
        totalResults = totalResults

    )
}

fun MoviesDto.ResultDto.toResult(): Movies.Result {
    return Movies.Result(
        adult, backdropPath, genreIds,
        id, originalLanguage, originalTitle,
        overview, popularity, posterPath, releaseDate,
        title, video, voteAverage, voteCount
    )
}

fun List<MoviesDto.ResultDto?>.toResult(): List<Movies.Result> {
    val resultList = mutableListOf<Movies.Result>()
    for (i in this) {
        if (i == null) {
            throw Exception("null found in resultDto list")
        }
        resultList.add(i.toResult())
    }
    return resultList
}

fun TvShowsDto.toTvShows(): TvShows {
    return TvShows(
        page = page,
        totalPages = totalPages,
        results = results?.toResult2(),
        totalResults = totalResults

    )
}

fun TvShowsDto.ResultDto2.toResult2(): TvShows.Result {
    return TvShows.Result(
        adult, backdropPath, firstAirDate,
        genreIds, id, name, originCountry,
        originalLanguage, originalName, overview,
        popularity, posterPath, voteAverage, voteCount

    )


}

fun List<TvShowsDto.ResultDto2?>.toResult2(): List<TvShows.Result> {
    val resultList = mutableListOf<TvShows.Result>()
    for (i in this) {
        if (i == null) {
            throw Exception("null found in resultDto list")
        }
        resultList.add(i.toResult2())
    }
    return resultList
}

fun PersonDto.toPerson(): Person {
    return Person(
        page, results?.toPersonResultList(), totalPages, totalResults
    )
}

fun List<PersonDto.Result?>.toPersonResultList(): List<Person.Result> {
    val resultList = mutableListOf<Person.Result>()
    for (i in this) {
        if (i == null) {
            throw Exception("null found in resultDto list")
        }
        resultList.add(i.toPersonResult())
    }
    return resultList
}

fun PersonDto.Result.toPersonResult(): Person.Result {
    return Person.Result(
        adult,
        gender,
        id,
        knownFor?.toPersonKnownForList(),
        knownForDepartment,
        mediaType,
        name,
        originalName,
        popularity,
        profilePath
    )
}

fun List<PersonDto.Result.KnownFor?>.toPersonKnownForList(): List<Person.Result.KnownFor> {
    val resultList = mutableListOf<Person.Result.KnownFor>()
    for (i in this) {
        if (i == null) {
            throw Exception("null found in resultDto list")
        }
        resultList.add(i.toPersonKnownFor())
    }
    return resultList
}

fun PersonDto.Result.KnownFor.toPersonKnownFor(): Person.Result.KnownFor {
    return Person.Result.KnownFor(
        adult, backdropPath, firstAirDate,
        genreIds, id, mediaType, name,
        originCountry, originalLanguage,
        originalName, originalTitle, overview,
        popularity, posterPath, releaseDate,
        title, video, voteAverage, voteCount
    )
}

private fun MovieGenreDto.Genre.toGenre(): Genre.Genre{
    return Genre.Genre(
        id,name
    )
}

private fun List<MovieGenreDto.Genre?>.toGenreList():List<Genre.Genre>{
    val resultList = mutableListOf<Genre.Genre>()

    this.map {
        it?.let {
            resultList.add(it.toGenre())
        }
    }
    return resultList
}

fun MovieGenreDto.toGenre():Genre{
    return Genre(
        genres = genres?.toGenreList()
    )
}

private fun TvGenreDto.GenreDto.toGenre():TvGenre.Genre{
    return TvGenre.Genre(
        id,name
    )
}

private fun List<TvGenreDto.GenreDto?>.toTvGenreList():List<TvGenre.Genre>{
    val resultList = mutableListOf<TvGenre.Genre>()

    this.map {
        it?.let {
            resultList.add(it.toGenre())
        }
    }
    return resultList
}

fun TvGenreDto.toTvGenre():TvGenre{
    return TvGenre(
        genres = genres?.toTvGenreList()
    )
}