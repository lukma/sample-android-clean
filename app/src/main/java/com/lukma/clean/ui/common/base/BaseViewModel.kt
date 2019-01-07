package com.lukma.clean.ui.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val useCaseJob = Job()

    override val coroutineContext: CoroutineContext
        get() = useCaseJob + Dispatchers.IO

    protected fun Job.addToJob() {
        runBlocking<Unit> {
            supervisorScope {
                this@addToJob
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCaseJob.cancel()
    }
}
