package com.lukma.clean.data.common.helper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object RepositoryHelper {
    fun <T> runAsync(block: suspend () -> T) = CoroutineScope(Dispatchers.IO).async {
        block.invoke()
    }
}
