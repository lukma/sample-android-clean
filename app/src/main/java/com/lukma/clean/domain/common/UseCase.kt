package com.lukma.clean.domain.common

import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent

abstract class UseCase<Entity> : KoinComponent {
    abstract fun build(params: Map<String, Any?> = emptyMap()): Deferred<Entity>

    fun execute(
        params: Map<String, Any?> = emptyMap(),
        onSuccess: (Entity) -> Unit = {},
        onError: (Throwable) -> Unit = { it.printStackTrace() }
    ) = CoroutineScope(Dispatchers.IO).launch(
        CoroutineExceptionHandler { _, exception -> onError(exception) }
    ) {
        onSuccess(build(params).await())
    }
}
