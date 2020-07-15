package com.lukma.core.domain

sealed class Either<out E, out V> {
    data class Error<out E>(val error: E) : Either<E, Nothing>()
    data class Value<out V>(val value: V) : Either<Nothing, V>()
}

fun <V> Either<Throwable, V>.getOrNull() = when (this) {
    is Either.Value -> value
    is Either.Error -> null
}

inline val <T> Either<Throwable, T>.isSuccess: Boolean
    get() = this is Either.Value

inline val <T> Either<Throwable, T>.eventState: EventState<T>
    get() = when (this) {
        is Either.Value -> EventState.Success(value)
        is Either.Error -> EventState.Failure(error)
    }
