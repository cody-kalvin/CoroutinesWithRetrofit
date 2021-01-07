package com.cody.coroutineswithretrofit.helper

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cody.coroutineswithretrofit.GlideApp
import com.cody.coroutineswithretrofit.R
import com.cody.coroutineswithretrofit.data.movie.MovieSearchResult
import com.cody.coroutineswithretrofit.ui.movie.search.MovieListAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@BindingAdapter("movieReleaseDate")
fun TextView.setMovieReleaseDate(releaseDate: String?) {
    this.text = try {
        val date = LocalDate.parse(releaseDate)
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
        formatter.format(date)
    } catch (e: Exception) {
        null
    }
}

@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(posterPath: String?) {
    GlideApp
        .with(this.context)
        .load(Link.IMG_URL + posterPath)
        .centerCrop()
        .placeholder(R.drawable.poster_placeholder)
        .into(this)
}

@BindingAdapter("searchResult")
fun ProgressBar.setSearchVisibility(result: MovieSearchResult) {
    this.visibility = if (result == MovieSearchResult.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("searchResult")
fun RecyclerView.setSearchResult(result: MovieSearchResult) {
    if (result is MovieSearchResult.Error) {
        Toast.makeText(this.context, result.message, Toast.LENGTH_SHORT).show()
    }

    val list = if (result is MovieSearchResult.Success && result.results.isNotEmpty()) {
        result.results.map { movie ->
            MovieListAdapter.MovieListItem.Body(movie)
        }
    } else {
        listOf(MovieListAdapter.MovieListItem.Empty)
    }
    (this.adapter as? MovieListAdapter)?.submitList(list)
}