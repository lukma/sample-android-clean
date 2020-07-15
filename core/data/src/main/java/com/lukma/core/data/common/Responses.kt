package com.lukma.core.data.common

import java.io.IOException

data class BaseResponse<T>(
    val data: T? = null,
    val error: ErrorResponse? = null
)

data class ErrorResponse(
    val code: String?,
    val description: String?
)

class ApiException(error: ErrorResponse) : IOException()

fun <T> BaseResponse<T>.getDataOrThrow() = data
    ?: throw error?.let { ApiException(it) } ?: Exception("Api response can't be blank")
