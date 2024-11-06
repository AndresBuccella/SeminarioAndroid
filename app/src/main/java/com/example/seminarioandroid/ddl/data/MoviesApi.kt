package com.example.seminarioandroid.ddl.data

import com.example.seminarioandroid.ddl.data.dtos.ListPopularMoviesDto
import com.example.seminarioandroid.ddl.data.dtos.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        //@Query("language") language: String,
        @Query("api_key") apiKey: String
        ): Response<ListPopularMoviesDto>

    @GET("movie/{id}")
    suspend fun getSpecifiedMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ):Response<MovieDto>
}