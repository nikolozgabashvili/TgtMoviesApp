package com.example.tgtmoviesapp.application.data.remote.mappers


import com.example.tgtmoviesapp.application.data.modelsDto.AllItemModelDto
import com.example.tgtmoviesapp.application.data.modelsDto.CastAndCrewDto
import com.example.tgtmoviesapp.application.data.modelsDto.CreatorsDto
import com.example.tgtmoviesapp.application.data.modelsDto.GenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.GetCurrentUserDto
import com.example.tgtmoviesapp.application.data.modelsDto.LanguagesDto
import com.example.tgtmoviesapp.application.data.modelsDto.MovieByPersonDto
import com.example.tgtmoviesapp.application.data.modelsDto.MovieDetailsDto
import com.example.tgtmoviesapp.application.data.modelsDto.MovieGenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.MovieVideoDto
import com.example.tgtmoviesapp.application.data.modelsDto.MoviesDto
import com.example.tgtmoviesapp.application.data.modelsDto.NetworkDto
import com.example.tgtmoviesapp.application.data.modelsDto.PersonDetailsDto
import com.example.tgtmoviesapp.application.data.modelsDto.PersonDto
import com.example.tgtmoviesapp.application.data.modelsDto.RegisterResponseDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvGenreDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvShowByPersonDto
import com.example.tgtmoviesapp.application.data.modelsDto.TvShowsDto
import com.example.tgtmoviesapp.application.data.modelsDto.UserFavoriteMovies
import com.example.tgtmoviesapp.application.domain.models.AllItemModel
import com.example.tgtmoviesapp.application.domain.models.Creators
import com.example.tgtmoviesapp.application.domain.models.CurrentUserModel
import com.example.tgtmoviesapp.application.domain.models.FavMovieId
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Languages
import com.example.tgtmoviesapp.application.domain.models.MovieAdd
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.MovieGenre
import com.example.tgtmoviesapp.application.domain.models.MovieVideo
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.PersonDetails
import com.example.tgtmoviesapp.application.domain.models.ProductionCompanies
import com.example.tgtmoviesapp.application.domain.models.RegisterResponse
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
        adult, backdropPath, genreIds.toMovieGenre(),
        id, originalLanguage, originalTitle,
        overview, popularity, posterPath, releaseDate,
        title, video, voteAverage, voteCount
    )
}

private fun List<MovieDetailsDto.ProductionCompany?>?.toProductCompsList(): List<ProductionCompanies> {
    val tempList = mutableListOf<ProductionCompanies>()
    this?.map {
        it?.let {

            tempList.add(it.toProductComps())
        }
    }
    return tempList
}

private fun MovieDetailsDto.ProductionCompany?.toProductComps(): ProductionCompanies {
    return ProductionCompanies(this?.id, this?.logoPath, this?.name ?: "", this?.originCountry)
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
        genreIds.toTvGenre(), id, name, originCountry,
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
        profilePath,
        role = null
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

private fun MovieGenreDto.Genre.toGenre(): Genre {
    return Genre(
        id, name
    )
}

private fun List<MovieGenreDto.Genre?>.toGenreList(): List<Genre> {
    val resultList = mutableListOf<Genre>()

    this.map {
        it?.let {
            resultList.add(it.toGenre())
        }
    }
    return resultList
}

fun MovieGenreDto.toGenre(): MovieGenre {
    return MovieGenre(
        genres = genres?.toGenreList()
    )
}

private fun GenreDto.toGenre(): Genre {
    return Genre(
        id, name
    )
}

private fun List<GenreDto?>.toTvGenreList(): List<Genre> {
    val resultList = mutableListOf<Genre>()

    this.map {
        it?.let {
            resultList.add(it.toGenre())
        }
    }
    return resultList
}

fun TvGenreDto.toTvGenre(): TvGenre {
    return TvGenre(
        genres = genres?.toTvGenreList()
    )
}

fun AllItemModelDto.toAllItemModel(): AllItemModel {
    return AllItemModel(
        page, results?.toAllItemModelList(), totalPages, totalResults
    )
}

private fun AllItemModelDto.Result.toAllItemResult(): AllItemModel.Result {

    return AllItemModel.Result(
        adult,
        backdropPath,
        firstAirDate,
        gender,
        genreIds,
        id,
        knownFor?.toPersonKnownForList(),
        knownForDepartment,
        mediaType,
        name,
        originCountry,
        originalLanguage,
        originalName,
        originalTitle,
        overview,
        popularity,
        posterPath,
        profilePath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount

    )
}

private fun List<AllItemModelDto.Result?>.toAllItemModelList(): List<AllItemModel.Result> {
    val resultList = mutableListOf<AllItemModel.Result>()

    this.map {
        it?.let {
            resultList.add(it.toAllItemResult())
        }
    }
    return resultList
}

fun RegisterResponseDto.toRegisterResponse(): RegisterResponse {
    return RegisterResponse(email, id, password, userFavoriteMovies, username)

}

fun String.toStringList(): List<String> {
    return listOf(this)
}

fun GetCurrentUserDto.toCurrentUserModel(): CurrentUserModel {
    return CurrentUserModel(email, id, password, userFavoriteMovies.toUserFavouriteList(), username)
}

fun UserFavoriteMovies.toUserMovies(): CurrentUserModel.UserFavoriteMovies {
    return CurrentUserModel.UserFavoriteMovies(movieId)
}

fun List<UserFavoriteMovies?>?.toUserFavouriteList(): List<CurrentUserModel.UserFavoriteMovies?> {
    val tempList = mutableListOf<CurrentUserModel.UserFavoriteMovies?>()
    this?.map {
        tempList.add(it?.toUserMovies())
    }
    return tempList
}

private fun List<MovieDetailsDto.SpokenLanguage?>?.toLang(): String {
    this?.map {
        it?.englishName?.let {
            return it
        }
    }
    return ""

}

private fun List<CreatorsDto>.toTvCreatorsList(): List<Creators> {
    val tempList = mutableListOf<Creators>()
    this.map {
        tempList.add(
            Creators(
                id = it.id,
                creditId = it.creditId,
                name = it.name,
                gender = it.gender?.toGender(),
                profilePath = it.profilePath
            )
        )
    }
    return tempList
}

private fun Int.toGender(): String {
    return when (this) {
        0 -> "Not specified"
        1 -> "Female"
        2 -> "Male"
        else -> {
            "Non-binary"
        }
    }
}

fun PersonDetailsDto.toPersonDetails(): PersonDetails {
    return PersonDetails(
        adult, biography,
        birthday,
        deathDay, gender,
        homepage, id,
        imdbId,
        knownForDepartment,
        name, placeOfBirth,
        popularity, profilePath
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    println(backdropPath)
    println("-----------")
    println(this.network)
    return MovieDetails(
        adult = adult,
        network = network?.nameList(),
        backdropPath = backdropPath,
        createdBy = createdBy?.toTvCreatorsList(),
        budget = budget,
        genres = genres?.toTvGenreList(),
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        spokenLanguage = spokenLanguages.toLang(),
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        productionCompanies = productionCompanies.toProductCompsList(),
        firstAirDate,
        originCountry,
        name

    )
}

val movieGenreMap = listOf(
    mapOf("id" to 28, "name" to "Action"),
    mapOf("id" to 12, "name" to "Adventure"),
    mapOf("id" to 16, "name" to "Animation"),
    mapOf("id" to 35, "name" to "Comedy"),
    mapOf("id" to 80, "name" to "Crime"),
    mapOf("id" to 99, "name" to "Documentary"),
    mapOf("id" to 18, "name" to "Drama"),
    mapOf("id" to 10751, "name" to "Family"),
    mapOf("id" to 14, "name" to "Fantasy"),
    mapOf("id" to 36, "name" to "History"),
    mapOf("id" to 27, "name" to "Horror"),
    mapOf("id" to 10402, "name" to "Music"),
    mapOf("id" to 9648, "name" to "Mystery"),
    mapOf("id" to 10749, "name" to "Romance"),
    mapOf("id" to 878, "name" to "Science Fiction"),
    mapOf("id" to 10770, "name" to "TV Movie"),
    mapOf("id" to 53, "name" to "Thriller"),
    mapOf("id" to 10752, "name" to "War"),
    mapOf("id" to 37, "name" to "Western")
)
val TvGenreMap = listOf(
    mapOf("id" to 10759, "name" to "Action & Adventure"),
    mapOf("id" to 16, "name" to "Animation"),
    mapOf("id" to 35, "name" to "Comedy"),
    mapOf("id" to 80, "name" to "Crime"),
    mapOf("id" to 99, "name" to "Documentary"),
    mapOf("id" to 18, "name" to "Drama"),
    mapOf("id" to 10751, "name" to "Family"),
    mapOf("id" to 10762, "name" to "Kids"),
    mapOf("id" to 9648, "name" to "Mystery"),
    mapOf("id" to 10763, "name" to "News"),
    mapOf("id" to 10764, "name" to "Reality"),
    mapOf("id" to 10765, "name" to "Sci-Fi & Fantasy"),
    mapOf("id" to 10766, "name" to "Soap"),
    mapOf("id" to 10767, "name" to "Talk"),
    mapOf("id" to 10768, "name" to "War & Politics"),
    mapOf("id" to 37, "name" to "Western")
)

fun List<Int?>?.toMovieGenre(): List<String> {
    val tempList = mutableListOf<String>()
    this?.map { value ->
        movieGenreMap.map {
            if (value == it["id"])
                tempList.add(it["name"] as String)
        }
    }
    return tempList
}

fun List<Int?>?.toTvGenre(): List<String> {
    val tempList = mutableListOf<String>()
    this?.map { value ->
        TvGenreMap.map {
            if (value == it["id"])
                tempList.add(it["name"] as String)
        }
    }
    return tempList
}

fun TvShowByPersonDto.toTvShows(): TvShows {
    return TvShows(null, this.toShowsResult(), null, null)
}

private fun TvShowByPersonDto?.toShowsResult(): List<TvShows.Result?> {
    val temp = mutableListOf<TvShows.Result?>()

    this?.cast?.map {
        temp.add(
            TvShows.Result(
                it?.adult,
                it?.backdropPath,
                it?.firstAirDate,
                it?.genreIds.toTvGenre(),
                it?.id,
                it?.name,
                it?.originCountry,
                it?.originalLanguage,
                it?.originalName,
                it?.overview,
                it?.popularity,
                it?.posterPath,
                it?.voteAverage,
                it?.voteCount
            )
        )
    }
    this?.crew?.map {
        temp.add(
            TvShows.Result(
                it?.adult,
                it?.backdropPath,
                it?.firstAirDate,
                it?.genreIds.toTvGenre(),
                it?.id,
                it?.name,
                it?.originCountry,
                it?.originalLanguage,
                it?.originalName,
                it?.overview,
                it?.popularity,
                it?.posterPath,
                it?.voteAverage,
                it?.voteCount
            )
        )


    }
    return temp

}


fun MovieByPersonDto.toMovie(): Movies {
    return Movies(null, this.toMoviesResult(), null, null)
}

private fun MovieByPersonDto?.toMoviesResult(): List<Movies.Result?> {
    val temp = mutableListOf<Movies.Result?>()
    this?.cast?.map {
        temp.add(
            Movies.Result(
                it?.adult,
                it?.backdropPath,
                it?.genreIds.toMovieGenre(),
                it?.id,
                it?.originalLanguage,
                it?.originalTitle,
                it?.overview,
                it?.popularity,
                it?.posterPath,
                it?.releaseDate,
                it?.title,
                it?.video,
                it?.voteAverage,
                it?.voteCount
            )
        )
    }
    this?.crew?.map {
        temp.add(
            Movies.Result(
                it?.adult,
                it?.backdropPath,
                it?.genreIds.toMovieGenre(),
                it?.id,
                it?.originalLanguage,
                it?.originalTitle,
                it?.overview,
                it?.popularity,
                it?.posterPath,
                it?.releaseDate,
                it?.title,
                it?.video,
                it?.voteAverage,
                it?.voteCount
            )
        )
    }
    return temp

}

private fun List<NetworkDto>.nameList(): List<String> {
    val temp = mutableListOf<String>()
    this.map {
        it.name?.let {

            temp.add(it)
        }
    }
    return temp

}


fun MovieVideoDto.toMovieVideo(): MovieVideo {
    return MovieVideo(id, results.toMovieResultList())
}

fun List<MovieVideoDto.Result?>?.toMovieResultList(): List<MovieVideo.Result?> {
    val tempList = mutableListOf<MovieVideo.Result>()
    this?.map {
        it?.let {
            tempList.add(it.toMovIeVideoResult())
        }

    }
    return tempList
}

fun MovieVideoDto.Result.toMovIeVideoResult(): MovieVideo.Result {
    return MovieVideo.Result(key)
}


fun CastAndCrewDto.toPerson(): Person {
    return Person(
        page = null,
        results = this.toPersonResultList(),
        totalPages = null,
        totalResults = null
    )
}

private fun CastAndCrewDto.toPersonResultList(): List<Person.Result> {
    val cast = this.cast
//    val crew = this.crew
    val personResultList = mutableListOf<Person.Result>()
    cast?.map {
        personResultList.add(
            Person.Result(
                adult = it?.adult,
                gender = it?.gender,
                id = it?.id,
                knownFor = null,
                knownForDepartment = it?.knownForDepartment,
                mediaType = null,
                name = it?.name,
                originalName = it?.originalName,
                profilePath = it?.profilePath,
                popularity = it?.popularity,
                role = it?.character

            )
        )
    }
//    crew?.map {
//        personResultList.add(Person.Result(
//            adult = it?.adult,
//            gender = it?.gender,
//            id,
//            knownFor = null,
//            knownForDepartment = it?.knownForDepartment,
//            mediaType = it?.department,
//            name = it?.name,
//            originalName = it?.originalName,
//            profilePath = it?.profilePath,
//            popularity = it?.popularity
//
//        ))
//    }
    return personResultList
}

fun MovieAdd.toMovieId(): FavMovieId {
    return FavMovieId(movieId = movieId)
}

fun LanguagesDto.toLanguage(): Languages {
    return Languages(languages.toLanguageList())
}

private fun LanguagesDto.LanguagesDtoItem.toLanguageItem(): Languages.LanguagesDtoItem {
    return Languages.LanguagesDtoItem(
        englishName, langCode, name
    )
}

private fun List<LanguagesDto.LanguagesDtoItem>.toLanguageList(): List<Languages.LanguagesDtoItem> {
    val tempList = mutableListOf<Languages.LanguagesDtoItem>()
    this.map {
        tempList.add(it.toLanguageItem())
    }
    return tempList
}
