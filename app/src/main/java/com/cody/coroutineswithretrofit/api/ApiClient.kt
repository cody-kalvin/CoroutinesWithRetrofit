package com.cody.coroutineswithretrofit.api

import com.cody.coroutineswithretrofit.helper.Link
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(private val client: OkHttpClient? = null) {
    private var _instance: Retrofit? = null

    val instance: Retrofit
        get() {
            val oldInstance = _instance

            return if (oldInstance == null) {
                val builder = Retrofit.Builder()
                builder.baseUrl(Link.API_URL)

                val okHttpClient = client
                if (okHttpClient != null) {
                    builder.client(okHttpClient)
                }
                builder.addConverterFactory(GsonConverterFactory.create())

                val newInstance = builder.build()
                _instance = newInstance

                newInstance
            } else {
                oldInstance
            }
        }
}