package com.example.tgtmoviesapp.application.commons.ext


import com.example.tgtmoviesapp.application.domain.models.Creators
import com.example.tgtmoviesapp.application.domain.models.ProductionCompanies
import kotlin.math.roundToInt

fun Int?.convert(): String {
    this?.let {
        return if (this in 1000..999999) {
            "$${(this / 1000)*10/10.0} K"
        } else if (this >= 1000000) {
            "$${(this / 1000000)*10/10.0} million"
        } else {
            this.toString()
        }
    }
    return ""

}

fun List<ProductionCompanies>.toCompanyList():String{
    var tempString = ""
    this.map {
        tempString+=it.name+"\n"
    }
    return tempString
}

fun List<Creators>.toCreatorList():String{
    var tempString = ""
    this.map {
        tempString += if (this.indexOf(it)!=this.size-1)
            it.name+","+"\n"
        else
            it.name
    }
    return tempString
}

fun String?.toDate(): String {
    val year = this?.substring(0, 4)
    val month = this?.substring(5, 7)
    val day = this?.substring(8, 10)
    return "$day ${month?.toMonth()} $year"
}

private fun String.toMonth(): String {
    return when (this) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> {""}
    }

}
