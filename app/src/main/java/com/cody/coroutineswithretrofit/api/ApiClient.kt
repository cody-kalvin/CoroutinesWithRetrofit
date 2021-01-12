package com.cody.coroutineswithretrofit.api

import com.cody.coroutineswithretrofit.helper.GsonUtil
import com.cody.coroutineswithretrofit.helper.Link
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    @Volatile
    private var INSTANCE: Retrofit? = null

    fun getInstance(client: OkHttpClient? = null): Retrofit {
        return INSTANCE
            ?: synchronized(this) {
                val oldInstance = INSTANCE
                return if (oldInstance == null) {
                    val builder = Retrofit.Builder()
                    builder.baseUrl(Link.API_URL)
                    if (client != null) {
                        builder.client(client)
                    }
                    builder.addConverterFactory(GsonConverterFactory.create(GsonUtil.instance))

                    val newInstance = builder.build()
                    INSTANCE = newInstance

                    newInstance
                } else {
                    oldInstance
                }
            }
    }
}