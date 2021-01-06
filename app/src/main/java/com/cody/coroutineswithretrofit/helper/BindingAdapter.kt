package com.cody.coroutineswithretrofit.helper

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cody.coroutineswithretrofit.GlideApp
import com.cody.coroutineswithretrofit.R
import com.cody.coroutineswithretrofit.data.movie.MovieSearchResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@BindingAdapter("movieReleaseDate")
fun TextView.setMovieReleaseDate(releaseDate: String?) {
    text = try {
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
    visibility = if (result == MovieSearchResult.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}