package com.lukma.clean.ui.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val supervisorJob = Job()

    override val coroutineContext: CoroutineContext
        get() = supervisorJob + Dispatchers.IO

    protected fun Job.runBySupervisor() {
        runBlocking<Unit> {
            supervisorScope {
                this@runBySupervisor
            }
        }
    }

    override fun onCleared() {
        supervisorJob.cancel()
    }
}
