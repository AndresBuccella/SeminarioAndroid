package com.example.seminarioandroid.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminarioandroid.R
import com.example.seminarioandroid.ddl.data.MoviesRepository
import com.example.seminarioandroid.ddl.models.PopularMovie
import com.example.seminarioandroid.ddl.models.SpecifiedMovie
import com.example.seminarioandroid.exceptions.ExceptionHandler
import com.example.seminarioandroid.providers.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val resourceProvider: ResourceProvider
    //private val savedStateHandle: SavedStateHandle //min 30 aprox vid 09
) : ViewModel() {

    private val _specifiedMovie = MutableStateFlow<SpecifiedMovie>(
        SpecifiedMovie(
            false,
            "",
            -1,
            "",
            "",
            "",
            ""
        ) as SpecifiedMovie
    )
    val specifiedMovie = _specifiedMovie.asStateFlow()

    private val _page = MutableStateFlow(value = 1 as Int)
    val page = _page.asStateFlow()

    private val _loading = MutableStateFlow(value = false)
    val loading = _loading.asStateFlow()

    private val _specificError = MutableStateFlow("")
    val specificError = _specificError.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.handleException(exception, resourceProvider)
        _specificError.value = message
    }

    private val _popularMovies = MutableStateFlow<List<PopularMovie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()


    fun getPopularMovies() {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            _specificError.value = ""
            _popularMovies.value = emptyList()

            try {
                val movies = moviesRepository.getPopularMovies(page.value)
                if (movies != null) {
                    if (movies.results.isEmpty())
                        _specificError.value = resourceProvider.getString(R.string.no_movies_found)
                    else{
                        _popularMovies.value = movies.toNormalListPopularMovie()
                        _page.value += 1
                    }
                } else
                    _specificError.value = resourceProvider.getString(R.string.no_movies_found)
            } catch (e: Exception) {
                _specificError.value = ExceptionHandler.handleException(e, resourceProvider)
            } finally {
                _loading.value = false
            }
        }
    }

    fun getSpecifiedMovie(idMovie: Int) {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            _specificError.value = ""
            _specifiedMovie.value =
                SpecifiedMovie(
                    false,
                    "", -1,
                    "", "",
                    "",
                    ""
                )

            try {
                val movie = moviesRepository.getSpecifiedMovieDetails(idMovie)
                if (movie != null)
                    _specifiedMovie.value = movie.toSpecifiedMovie()
                else
                    _specificError.value = "Movie details not found"

            }catch (e: Exception) {
                _specificError.value = ExceptionHandler.handleException(e, resourceProvider)
            } finally {
                _loading.value = false
            }
        }
    }
}