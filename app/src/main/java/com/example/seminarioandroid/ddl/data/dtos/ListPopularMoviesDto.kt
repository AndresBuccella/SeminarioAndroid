package com.example.seminarioandroid.ddl.data.dtos

import androidx.annotation.Keep
import com.example.seminarioandroid.ddl.models.PopularMovie
import com.google.gson.annotations.SerializedName

@Keep
data class ListPopularMoviesDto (
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int

){
    fun toNormalListPopularMovie(): List<PopularMovie>{
        return results.map { it.toPopularMovie() }
    }
}