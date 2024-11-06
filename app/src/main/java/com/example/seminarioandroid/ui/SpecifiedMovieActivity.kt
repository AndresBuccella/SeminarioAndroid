package com.example.seminarioandroid.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.seminarioandroid.R
import com.example.seminarioandroid.databinding.ActivitySpecifiedMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SpecifiedMovieActivity: AppCompatActivity() {


    private lateinit var binding: ActivitySpecifiedMovieBinding


    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpecifiedMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToUi()
        subscribeToViewModel()
    }
    private fun subscribeToUi(){
        viewModel.getSpecifiedMovie(intent.getIntExtra("movie_id", 0))
    }

    private fun subscribeToViewModel() {

        binding.retryButton.setOnClickListener {
            viewModel.getSpecifiedMovie(intent.getIntExtra("movie_id", 0))
        }

        viewModel.loading.onEach { loading ->
            binding.movieContainer.visibility = if (loading) View.INVISIBLE else View.VISIBLE

            binding.imageProgressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
            binding.generalProgressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        }.launchIn(lifecycleScope)

        viewModel.specifiedMovie.onEach { movie ->
            movie.let {
                showMovie()
            }
        }.launchIn(lifecycleScope)

        viewModel.specificError.onEach { error ->
            if (error.isNotBlank()) {
                Toast.makeText(this, viewModel.specificError.value, Toast.LENGTH_SHORT)
                    .show()
                binding.retryButton.visibility = View.VISIBLE
            } else
                binding.retryButton.visibility = View.INVISIBLE

        }.launchIn(lifecycleScope)
    }

    private fun showMovie() {
        binding.imageProgressBar.visibility = View.VISIBLE
        Glide.with(binding.root.context)
            .load(viewModel.specifiedMovie.value.getUrlImg())
            .error(R.drawable.baseline_broken_image_24)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.imageProgressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.imageProgressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.movieImage)

        val movie = viewModel.specifiedMovie.value
        binding.movieTitle.text = movie.title
        binding.ratingText.text = movie.voteAverage
        binding.genresText.text = movie.genres
        binding.overviewText.text = movie.overview
    }
}