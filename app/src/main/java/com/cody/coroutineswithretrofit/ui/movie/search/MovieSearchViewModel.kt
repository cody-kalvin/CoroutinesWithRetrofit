package com.cody.coroutineswithretrofit.ui.movie.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cody.coroutineswithretrofit.api.ApiResult
import com.cody.coroutineswithretrofit.data.movie.MovieRepository
import com.cody.coroutineswithretrofit.data.movie.MovieSearchResult
import kotlinx.coroutines.launch

class MovieSearchViewModel(
    application: Application,
    private val repository: MovieRepository
) : AndroidViewModel(application) {
    val query: MutableLiveData<String> = MutableLiveData()

    private val _searchResult = MutableLiveData<MovieSearchResult>(MovieSearchResult.Initial)
    val searchResult: LiveData<MovieSearchResult> = _searchResult

    fun search() = viewModelScope.launch {
        _searchResult.value = MovieSearchResult.Loading

        val query = query.value ?: ""
        if (query.isBlank()) {
            _searchResult.value = MovieSearchResult.Success(emptyList())
        } else {
            val result = repository.search(query)
            _searchResult.value = when (result) {
                is ApiResult.ResponseSuccess -> MovieSearchResult.Success(result.value.results)
                is ApiResult.ResponseFailure -> MovieSearchResult.Error(result.error)
                is ApiResult.NetworkFailure -> MovieSearchResult.Error(result.error)
            }
        }
    }
}