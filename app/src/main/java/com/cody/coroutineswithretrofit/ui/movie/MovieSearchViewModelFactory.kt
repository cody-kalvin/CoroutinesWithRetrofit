package com.cody.coroutineswithretrofit.ui.movie

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cody.coroutineswithretrofit.api.ApiClient
import com.cody.coroutineswithretrofit.api.ApiService
import com.cody.coroutineswithretrofit.data.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalArgumentException

class MovieSearchViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieSearchViewModel::class.java)) {
            val client = ApiClient()
            val retrofit = client.retrofitInstance
            val service = retrofit!!.create(ApiService::class.java) as ApiService
            val repository = MovieRepository(service, Dispatchers.IO)
            return MovieSearchViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}