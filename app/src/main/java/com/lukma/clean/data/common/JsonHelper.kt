package com.lukma.clean.data.common

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

class JsonHelper {
    companion object {
        private var instance: JsonHelper? = null

        fun getInstance(): JsonHelper? {
            if (instance == null) {
                instance = JsonHelper()
            }

            return instance
        }
    }

    val objectMapper: ObjectMapper = ObjectMapper()

    init {
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)

    }

    inline fun <reified T> stringToObject(value: String?): T {
        return objectMapper.readValue(value, T::class.java)
    }
}