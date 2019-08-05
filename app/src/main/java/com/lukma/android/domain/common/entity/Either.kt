package com.lukma.android.domain.common.entity

sealed class Either<out E, out V> {
    data class Error<out E>(val error: E) : Either<E, Nothing>()
    data class Value<out V>(val value: V) : Either<Nothing, V>()
}

suspend fun <V> either(block: suspend () -> V): Either<Throwable, V> =
    try {
        Either.Value(block())
    } catch (e: Exception) {
        Either.Error(e)
    }
