package com.cody.coroutineswithretrofit.data.movie

import com.cody.coroutineswithretrofit.model.Movie

sealed class MovieSearchResult {
    object Initial : MovieSearchResult()
    object Loading : MovieSearchResult()
    class Success(val results: List<Movie>) : MovieSearchResult()
    class Error(val message: String) : MovieSearchResult()
}