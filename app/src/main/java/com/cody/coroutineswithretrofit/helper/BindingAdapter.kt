package com.cody.coroutineswithretrofit.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cody.coroutineswithretrofit.GlideApp
import com.cody.coroutineswithretrofit.R
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