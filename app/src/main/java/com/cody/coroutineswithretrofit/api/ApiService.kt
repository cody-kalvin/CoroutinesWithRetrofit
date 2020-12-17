package com.cody.coroutineswithretrofit.api

import com.cody.coroutineswithretrofit.data.movie.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // TODO: add apiKey via interceptor
    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieListResponse>
}