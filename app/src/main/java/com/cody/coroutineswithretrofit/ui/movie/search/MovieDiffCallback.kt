package com.cody.coroutineswithretrofit.ui.movie.search

import androidx.recyclerview.widget.DiffUtil
import com.cody.coroutineswithretrofit.ui.movie.search.MovieListAdapter.MovieListItem

class MovieDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return when {
            oldItem is MovieListItem.Error && newItem is MovieListItem.Error -> {
                true
            }
            oldItem is MovieListItem.Body && newItem is MovieListItem.Body -> {
                oldItem.movie.id == newItem.movie.id
            }
            oldItem is MovieListItem.Empty && newItem is MovieListItem.Empty -> {
                oldItem.message == newItem.message
            }
            else -> {
                false
            }
        }
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return when {
            oldItem is MovieListItem.Error && newItem is MovieListItem.Error -> {
                true
            }
            oldItem is MovieListItem.Body && newItem is MovieListItem.Body -> {
                oldItem.movie == newItem.movie
            }
            oldItem is MovieListItem.Empty && newItem is MovieListItem.Empty -> {
                oldItem.message == newItem.message
            }
            else -> {
                false
            }
        }
    }
}