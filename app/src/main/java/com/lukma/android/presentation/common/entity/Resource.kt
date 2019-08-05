package com.lukma.android.presentation.common.entity

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: Throwable) : Resource<Nothing>()
}
