package com.lukma.core.domain

sealed class EventState<out T> {
    object Loading : EventState<Nothing>()
    data class Success<T>(val value: T) : EventState<T>()
    data class Failure(val error: Throwable) : EventState<Nothing>()
}

inline val <T> EventState<T>.isLoading: Boolean
    get() = this is EventState.Loading
