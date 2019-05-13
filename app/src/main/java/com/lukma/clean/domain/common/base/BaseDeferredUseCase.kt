package com.lukma.clean.domain.common.base

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseDeferredUseCase<Entity> : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    abstract suspend fun build(params: Map<String, Any?> = emptyMap()): Deferred<Entity>

    fun execute(
        params: Map<String, Any?> = emptyMap(),
        onSuccess: (Entity) -> Unit = {},
        onError: (Throwable) -> Unit = { it.printStackTrace() }
    ) = launch(CoroutineExceptionHandler { _, exception -> onError(exception) }) {
        onSuccess(build(params).await())
    }
}
