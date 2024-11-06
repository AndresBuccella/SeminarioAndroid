package com.example.seminarioandroid.ddl.data.dtos

import androidx.annotation.Keep
import com.example.seminarioandroid.ddl.models.PopularMovie
import com.example.seminarioandroid.ddl.models.SpecifiedMovie
import com.google.gson.annotations.SerializedName

@Keep
data class MovieDto (
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("genres")
    val genreIds: List<GenreDto>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double

    ){
    fun toPopularMovie(): PopularMovie {
        return PopularMovie(
            adult = adult,
            id = id,
            posterPath = posterPath,
            title = title
        )
    }
    fun toSpecifiedMovie(): SpecifiedMovie {

        val genresString = if (genreIds.isEmpty()) {
            "Sin géneros"
        } else {
            genreIds.map { genre -> genre.name
            }.joinToString(", ")
        }

        return SpecifiedMovie(
            adult = adult,
            genres = genresString,
            id = id,
            overview = overview,
            posterPath = posterPath,
            title = title,
            voteAverage = "$voteAverage/10",
        )
    }
}