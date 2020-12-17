package com.cody.coroutineswithretrofit.data.movie

import com.cody.coroutineswithretrofit.api.ApiResult
import com.cody.coroutineswithretrofit.api.ApiService
import com.cody.coroutineswithretrofit.helper.Link
import com.cody.coroutineswithretrofit.helper.apiCall
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.await

class MovieRepository(
    private val service: ApiService,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun search(query: String): ApiResult<MovieListResponse> = apiCall(dispatcher) {
        service.searchMovies(Link.API_KEY, query).await()
    }
}