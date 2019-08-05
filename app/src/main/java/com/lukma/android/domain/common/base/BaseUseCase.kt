package com.lukma.android.domain.common.base

import com.lukma.android.domain.common.entity.either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<Entity> {
    protected lateinit var params: Map<String, Any?>

    fun addParams(params: Map<String, Any?>) = apply {
        this.params = params
    }

    abstract suspend fun build(): Entity

    suspend fun invoke(context: CoroutineContext = Dispatchers.IO) = withContext(context) {
        either(::build)
    }
}
