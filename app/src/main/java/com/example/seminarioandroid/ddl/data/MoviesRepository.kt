package com.example.seminarioandroid.ddl.data

import com.example.seminarioandroid.ddl.data.dtos.ListPopularMoviesDto
import com.example.seminarioandroid.ddl.data.dtos.MovieDto
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MovieRemoteDataSource
) {

    suspend fun getPopularMovies(
        page: Int
    ): ListPopularMoviesDto?{
        return moviesRemoteDataSource.getPopularMovies(page)
    }

    suspend fun getSpecifiedMovieDetails(
        id: Int
    ): MovieDto?{
        return moviesRemoteDataSource.getSpecifiedMovie(id)
    }
}