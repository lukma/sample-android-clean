package com.lukma.android.domain.common.base

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseUseCase<Entity> {
    private var params: Map<String, Any?> = emptyMap()
    private var onSuccess: (Entity) -> Unit = {}
    private var onError: (Throwable) -> Unit = { it.printStackTrace() }

    abstract suspend fun build(params: Map<String, Any?> = emptyMap()): Entity

    fun addParams(params: Map<String, Any?>) = apply {
        this.params = params
    }

    fun onSuccess(onSuccess: (Entity) -> Unit) = apply {
        this.onSuccess = onSuccess
    }

    fun onError(onError: (Throwable) -> Unit) = apply {
        this.onError = onError
    }

    fun execute(scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) =
        scope.launch(CoroutineExceptionHandler { _, exception -> onError(exception) }) {
            onSuccess(build(params))
        }
}
