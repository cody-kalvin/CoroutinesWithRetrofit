package com.cody.coroutineswithretrofit.data.movie

import com.cody.coroutineswithretrofit.model.Movie

sealed class MovieDetailResult {
    object Initial : MovieDetailResult()
    object Loading : MovieDetailResult()
    class Success(val movie: Movie) : MovieDetailResult()
    class Error(val message: String) : MovieDetailResult()
}