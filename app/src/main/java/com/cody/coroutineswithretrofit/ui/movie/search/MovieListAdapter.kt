package com.cody.coroutineswithretrofit.ui.movie.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cody.coroutineswithretrofit.databinding.ListItemEmptyBinding
import com.cody.coroutineswithretrofit.databinding.ListItemErrorBinding
import com.cody.coroutineswithretrofit.databinding.ListItemMovieBinding
import com.cody.coroutineswithretrofit.model.Movie

class MovieListAdapter(
    private val listener: OnItemClickListener? = null
) : ListAdapter<MovieListAdapter.MovieListItem, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun getItemViewType(position: Int): Int = getItem(position).itemType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieItemType.values()[viewType]) {
            MovieItemType.EMPTY -> EmptyViewHolder.from(parent)
            MovieItemType.BODY -> MovieViewHolder.from(parent)
            else -> ErrorViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MovieListItem.Empty -> {
                val vh = holder as EmptyViewHolder
                vh.bind(item.placeholder)
                vh.binding.root.setOnClickListener { listener?.onClick(item) }
            }
            is MovieListItem.Body -> {
                val vh = holder as MovieViewHolder
                vh.bind(item)
                vh.binding.root.setOnClickListener { listener?.onClick(item) }
            }
            MovieListItem.Error -> {
                val vh = holder as ErrorViewHolder
                vh.bind()
                vh.binding.actionRefresh.setOnClickListener { listener?.onClick(item) }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: MovieListItem)
    }

    enum class MovieItemType {
        EMPTY,
        BODY,
        ERROR
    }

    sealed class MovieListItem(val itemType: MovieItemType) {
        data class Empty(val placeholder: String) : MovieListItem(MovieItemType.EMPTY)
        data class Body(val movie: Movie) : MovieListItem(MovieItemType.BODY)
        object Error : MovieListItem(MovieItemType.ERROR)
    }

    class EmptyViewHolder(
        val binding: ListItemEmptyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeholder: String) {
            binding.placeholder = placeholder
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
        val binding: ListItemMovieBinding,
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
        val binding: ListItemErrorBinding
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
}