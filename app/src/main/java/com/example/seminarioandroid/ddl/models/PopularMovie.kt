package com.example.seminarioandroid.ddl.models

import com.example.seminarioandroid.BuildConfig


class PopularMovie (
    val adult: Boolean,
    //val genreIds: List<GenreDto>,
    val id: Int,
    val posterPath: String,
    val title: String,
){
    fun getUrlImg(): String{
        return BuildConfig.URL_IMAGE_BASE + posterPath
    }
}