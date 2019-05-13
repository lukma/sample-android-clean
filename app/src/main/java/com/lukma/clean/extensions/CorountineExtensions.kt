package com.lukma.clean.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun <T> runAsync(block: suspend () -> T) = CoroutineScope(Dispatchers.IO).async {
    block.invoke()
}

fun <T> T.toDeferred(): Deferred<T> = CoroutineScope(Dispatchers.IO).async {
    this@toDeferred
}
