package com.example.seminarioandroid.ddl.data.dtos

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)