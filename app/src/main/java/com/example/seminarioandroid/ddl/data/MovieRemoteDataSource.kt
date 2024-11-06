package com.example.seminarioandroid.ddl.data

import com.example.seminarioandroid.BuildConfig
import com.example.seminarioandroid.R
import com.example.seminarioandroid.ddl.data.dtos.ListPopularMoviesDto
import com.example.seminarioandroid.ddl.data.dtos.MovieDto
import com.example.seminarioandroid.providers.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi,
    private val provider: ResourceProvider
) {

    suspend fun getPopularMovies(
        page: Int
    ): ListPopularMoviesDto?{
        return withContext(Dispatchers.IO){
            val response = moviesApi.getPopularMovies(page, BuildConfig.API_KEY)

            if (!response.isSuccessful)
                return@withContext null

            return@withContext response.body()
        }
    }

    suspend fun getSpecifiedMovie(
        id: Int
    ): MovieDto?{
        return withContext(Dispatchers.IO){
            val response = moviesApi.getSpecifiedMovie(id, provider.getString(R.string.language), BuildConfig.API_KEY)
            if (!response.isSuccessful){
                return@withContext null
            }
            return@withContext response.body()
        }
    }
}
