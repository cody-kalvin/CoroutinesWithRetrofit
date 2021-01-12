package com.cody.coroutineswithretrofit.typeadapter

import com.cody.coroutineswithretrofit.model.Genre
import com.google.gson.*
import java.lang.reflect.Type

class GenreTypeAdapter : JsonSerializer<Genre?>, JsonDeserializer<Genre?> {
    override fun serialize(
        src: Genre?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.id)
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Genre? {
        return json?.run {
            Genre.fromInt(this@run.asInt)
        }
    }

}