package com.example.seminarioandroid.ddl.data.dtos

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class ListGenreDto (
    @SerializedName("genres")
    val genres : List<GenreDto>
)