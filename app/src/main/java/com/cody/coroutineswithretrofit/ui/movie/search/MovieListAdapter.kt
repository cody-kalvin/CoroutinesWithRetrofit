package com.cody.coroutineswithretrofit.ui.movie.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cody.coroutineswithretrofit.databinding.ListItemEmptyBinding
import com.cody.coroutineswithretrofit.databinding.ListItemErrorBinding
import com.cody.coroutineswithretrofit.databinding.ListItemMovieBinding
import com.cody.coroutineswithretrofit.model.Movie

class MovieListAdapter :
    ListAdapter<MovieListAdapter.MovieListItem, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            MovieListItem.Empty -> (holder as EmptyViewHolder).bind("Find your favorite movies here.")
            is MovieListItem.Body -> (holder as MovieViewHolder).bind(item)
            MovieListItem.Error -> (holder as ErrorViewHolder).bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieItemType.values()[viewType]) {
            MovieItemType.EMPTY -> EmptyViewHolder.from(parent)
            MovieItemType.BODY -> MovieViewHolder.from(parent)
            else -> ErrorViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).itemType.ordinal

    class EmptyViewHolder(
        private val binding: ListItemEmptyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: String) {
            binding.message = message
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEmptyBinding.inflate(layoutInflater, parent, false)
                return EmptyViewHolder(binding)
            }
        }
    }

    class MovieViewHolder private constructor(
        private val binding: ListItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var movie: Movie

        fun bind(item: MovieListItem.Body) {
            movie = item.movie
            binding.movie = movie
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding)
            }
        }
    }

    class ErrorViewHolder(
        private val binding: ListItemErrorBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ErrorViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemErrorBinding.inflate(layoutInflater, parent, false)
                return ErrorViewHolder(binding)
            }
        }
    }

    enum class MovieItemType {
        EMPTY,
        BODY,
        ERROR
    }

    sealed class MovieListItem(val itemType: MovieItemType) {
        object Empty : MovieListItem(MovieItemType.EMPTY)
        data class Body(val movie: Movie) : MovieListItem(MovieItemType.BODY)
        object Error : MovieListItem(MovieItemType.ERROR)
    }
}