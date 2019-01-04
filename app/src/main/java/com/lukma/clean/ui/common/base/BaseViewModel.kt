package com.lukma.clean.ui.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    private var useCaseJob: Job? = null

    protected fun Job.addToJob() {
        useCaseJob = this
    }

    override fun onCleared() {
        super.onCleared()
        useCaseJob?.cancel()
    }
}
