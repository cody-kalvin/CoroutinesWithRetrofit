package com.cody.coroutineswithretrofit.helper

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cody.coroutineswithretrofit.GlideApp
import com.cody.coroutineswithretrofit.R
import com.cody.coroutineswithretrofit.data.movie.MovieDetailResult
import com.cody.coroutineswithretrofit.data.movie.MovieSearchResult
import com.cody.coroutineswithretrofit.model.Genre
import com.cody.coroutineswithretrofit.ui.movie.search.MovieListAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
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

    val list = when {
        result is MovieSearchResult.Error -> {
            listOf(MovieListAdapter.MovieListItem.Error)
        }
        result is MovieSearchResult.Success && result.results.isNotEmpty() -> {
            result.results.map { movie ->
                MovieListAdapter.MovieListItem.Body(movie)
            }
        }
        result is MovieSearchResult.Loading -> {
            listOf(MovieListAdapter.MovieListItem.Empty(""))
        }
        else -> {
            listOf(MovieListAdapter.MovieListItem.Empty("Find your favorite movies here."))
        }
    }
    (this.adapter as? MovieListAdapter)?.submitList(list)
}

@BindingAdapter("detailResult")
fun ProgressBar.setDetailVisibility(result: MovieDetailResult) {
    this.visibility = if (result == MovieDetailResult.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("movieBackdrop")
fun ImageView.setMovieBackdrop(result: MovieDetailResult) {
    if (result is MovieDetailResult.Success) {
        this.visibility = View.VISIBLE
        GlideApp
            .with(this.context)
            .load(Link.IMG_URL + result.movie.backdropPath)
            .centerCrop()
            .placeholder(R.drawable.poster_placeholder)
            .into(this)
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("movieTitle")
fun TextView.setMovieTitle(result: MovieDetailResult) {
    this.text = if (result is MovieDetailResult.Success) {
        val string = SpannableString(result.movie.title)
        val boldFace = StyleSpan(Typeface.BOLD)
        string.setSpan(boldFace, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        string
    } else {
        null
    }
}

@BindingAdapter("movieGenre")
fun TextView.setMovieGenre(result: MovieDetailResult) {
    this.text = if (result is MovieDetailResult.Success) {
        result.movie.genres?.filterNotNull()?.joinToString(", ") { Genre.getTitle(it) }
    } else {
        null
    }
}

@BindingAdapter("movieOverviewHeader")
fun TextView.setMovieOverviewHeader(result: MovieDetailResult) {
    this.text = if (result is MovieDetailResult.Success) {
        val string = SpannableString("Overview")
        val boldFace = StyleSpan(Typeface.BOLD)
        string.setSpan(boldFace, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        string
    } else {
        null
    }
}

@BindingAdapter("movieOverviewBody")
fun TextView.setMovieOverviewBody(result: MovieDetailResult) {
    this.text = if (result is MovieDetailResult.Success) {
        result.movie.overview
    } else {
        null
    }
}

@BindingAdapter("detailResult", "isCollapsed")
fun CollapsingToolbarLayout.setMovieTitle(result: MovieDetailResult, isCollapsed: Boolean) {
    this.title = if (result is MovieDetailResult.Success && isCollapsed) {
        result.movie.title ?: " "
    } else {
        " "
    }
}