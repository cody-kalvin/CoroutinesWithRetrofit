package com.cody.coroutineswithretrofit.api

import com.cody.coroutineswithretrofit.helper.Link
import com.cody.coroutineswithretrofit.model.Genre
import com.cody.coroutineswithretrofit.typeadapter.GenreTypeAdapter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val client: OkHttpClient? = null) {
    private var _instance: Retrofit? = null

    val instance: Retrofit
        get() {
            val oldInstance = _instance

            return if (oldInstance == null) {
                val retrofitBuilder = Retrofit.Builder()
                retrofitBuilder.baseUrl(Link.API_URL)

                val okHttpClient = client
                if (okHttpClient != null) {
                    retrofitBuilder.client(okHttpClient)
                }

                val gsonBuilder = GsonBuilder().apply {
                    registerTypeAdapter(Genre::class.java, GenreTypeAdapter())
                }
                val gson = gsonBuilder.create()
                val gsonConverterFactory = GsonConverterFactory.create(gson)
                retrofitBuilder.addConverterFactory(gsonConverterFactory)

                val newInstance = retrofitBuilder.build()
                _instance = newInstance

                newInstance
            } else {
                oldInstance
            }
        }
}