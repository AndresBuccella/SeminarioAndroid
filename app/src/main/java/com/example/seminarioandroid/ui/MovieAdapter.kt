package com.example.seminarioandroid.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.seminarioandroid.R
import com.example.seminarioandroid.databinding.ListPopularMoviesBinding
import com.example.seminarioandroid.ddl.models.PopularMovie

class MovieAdapter(
    private val movies: List<PopularMovie>,
    private val onMovieClicked: (PopularMovie) -> Unit
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListPopularMoviesBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(
        private val binding: ListPopularMoviesBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(movie: PopularMovie){

            binding.progressBar.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(movie.getUrlImg())
                .error(R.drawable.baseline_clear_24)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imagePopularMovie)

            binding.movieTitle.text = movie.title.ifEmpty { "@string/no_title" }

            binding.root.setOnClickListener{
                onMovieClicked(movie)
            }
        }
    }
}

