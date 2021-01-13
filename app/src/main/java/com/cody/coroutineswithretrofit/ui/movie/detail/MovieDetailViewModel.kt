package com.cody.coroutineswithretrofit.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cody.coroutineswithretrofit.data.movie.MovieDetailResult
import com.cody.coroutineswithretrofit.model.Movie

class MovieDetailViewModel : ViewModel() {
    private val _detailResult = MutableLiveData<MovieDetailResult>(MovieDetailResult.Initial)
    val detailResult: LiveData<MovieDetailResult> = _detailResult

    private val _isCollapsed = MutableLiveData(false)
    val isCollapsed: LiveData<Boolean> = _isCollapsed

    fun setDetail(movie: Movie?) {
        _detailResult.value = MovieDetailResult.Loading

        if (movie == null) {
            _detailResult.value = MovieDetailResult.Error("Movie not found.")
        } else {
            _detailResult.value = MovieDetailResult.Success(movie)
        }
    }

    fun setCollapsed(collapsed: Boolean) {
        _isCollapsed.value = collapsed
    }
}