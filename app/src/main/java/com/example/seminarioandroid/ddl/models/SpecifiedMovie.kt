package com.example.seminarioandroid.ddl.models

import android.service.autofill.VisibilitySetterAction
import com.example.seminarioandroid.BuildConfig
import kotlin.reflect.KMutableProperty


class SpecifiedMovie (
    val adult: Boolean,
    val genres: String,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val title: String,
    val voteAverage: String,
){
    fun getUrlImg(): String{
        return (BuildConfig.URL_IMAGE_BASE + posterPath)
    }
}