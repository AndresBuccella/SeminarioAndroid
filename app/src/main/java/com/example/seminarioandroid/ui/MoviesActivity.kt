package com.example.seminarioandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seminarioandroid.databinding.ActivityPopularMoviesBinding
import com.example.seminarioandroid.providers.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MoviesActivity: AppCompatActivity() {


    private lateinit var binding: ActivityPopularMoviesBinding
    private lateinit var resourceProvider: ResourceProvider


    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPopularMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resourceProvider = ResourceProvider(this)

        subscribeToUi()
        subscribeToViewModel()
    }

    private fun subscribeToUi() {
        viewModel.getPopularMovies()
    }

    private fun subscribeToViewModel() {
        viewModel.loading.onEach { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
            binding.popularMovies.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        }.launchIn(lifecycleScope)

        viewModel.popularMovies.onEach { movies ->
            binding.popularMovies.adapter = MovieAdapter(
                movies ?: emptyList(),
                onMovieClicked = { movie ->
                    val intent = Intent(this, SpecifiedMovieActivity::class.java)
                    intent.putExtra("movie_id", movie.id)
                    startActivity(intent)
                })

        }.launchIn(lifecycleScope)

        viewModel.specificError.onEach { error ->
            if (error.isNotBlank()) {
                Toast.makeText(this, viewModel.specificError.value, Toast.LENGTH_SHORT)
                    .show()

                binding.retryButton.visibility = View.VISIBLE

                binding.retryButton.setOnClickListener {
                    viewModel.getPopularMovies()
                    binding.retryButton.visibility = View.INVISIBLE
                }
            } else{
                binding.retryButton.visibility = View.INVISIBLE
            }

        }.launchIn(lifecycleScope)


    }
}
    /*
            viewModel.error.onEach { error ->
                binding.errorMessage.visibility = if (error) View.VISIBLE else View.INVISIBLE
                binding.errorMessage.text = viewModel.errorEspecifico.toString()
            }.launchIn(lifecycleScope)*/
    /*
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.clearButton.visibility = if (s?.isNotEmpty() == true) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.clearButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }


*/
        /*
        binding.participantsEditText.addTextChangedListener { text ->
            val isValid = text?.toString()?.toIntOrNull() != null
            binding.recommendActivityButton.isEnabled = isValid
        }

        binding.recommendActivityButton.setOnClickListener {
            val participants = binding.participantsEditText.text.toString().toIntOrNull()
            if (participants != null)
                viewModel.getRecommendation(participants)
        }*/