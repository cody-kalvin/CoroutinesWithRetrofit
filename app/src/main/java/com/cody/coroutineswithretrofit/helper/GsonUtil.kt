package com.cody.coroutineswithretrofit.helper

import com.cody.coroutineswithretrofit.model.Genre
import com.cody.coroutineswithretrofit.typeadapter.GenreTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtil {
    val instance: Gson
        get() {
            val builder = GsonBuilder().apply {
                registerTypeAdapter(Genre::class.java, GenreTypeAdapter())
            }
            return builder.create()
        }
}