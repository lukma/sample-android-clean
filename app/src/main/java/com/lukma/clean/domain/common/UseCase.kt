package com.lukma.clean.domain.common

import kotlinx.coroutines.*

abstract class UseCase<Entity> {
    abstract fun build(params: Map<String, Any?> = emptyMap()): Deferred<Entity>

    fun execute(
        params: Map<String, Any?> = emptyMap(),
        onSuccess: (Entity) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) = CoroutineScope(Dispatchers.IO).launch(
        CoroutineExceptionHandler { _, exception -> onError(exception) }
    ) {
        onSuccess(build(params).await())
    }
}
