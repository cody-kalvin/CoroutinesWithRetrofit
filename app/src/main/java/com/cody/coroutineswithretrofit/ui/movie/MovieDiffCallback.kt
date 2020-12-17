package com.cody.coroutineswithretrofit.ui.movie

import androidx.recyclerview.widget.DiffUtil
import com.cody.coroutineswithretrofit.ui.movie.MovieListAdapter.MovieListItem

class MovieDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return if (oldItem is MovieListItem.Body && newItem is MovieListItem.Body) {
            oldItem.movie.id == newItem.movie.id
        } else {
            oldItem is MovieListItem.Empty && newItem is MovieListItem.Empty
        }
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return if (oldItem is MovieListItem.Body && newItem is MovieListItem.Body) {
            oldItem.movie == newItem.movie
        } else {
            oldItem is MovieListItem.Empty && newItem is MovieListItem.Empty
        }
    }
}