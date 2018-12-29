package com.lukma.clean.ui.common

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job

class SingleLiveData<Entity>(
    private val onRunning: (
        params: Map<String, Any?>,
        onSuccess: (Entity) -> Unit,
        onError: (Throwable) -> Unit
    ) -> Job
) : MutableLiveData<SingleLiveData.Resource<Entity>>() {

    private var job: Job? = null

    fun run(params: Map<String, Any?> = emptyMap()) {
        postValue(Resource(State.ON_REQUEST))
        job = onRunning(
            params,
            { postValue(Resource(State.ON_SUCCESS, data = it)) },
            { postValue(Resource(State.ON_FAILURE, error = it)) }
        )
    }

    override fun onInactive() {
        super.onInactive()
        job?.cancel()
    }

    data class Resource<Entity>(
        val state: State,
        val data: Entity? = null,
        val error: Throwable? = null
    )

    enum class State {
        ON_REQUEST,
        ON_SUCCESS,
        ON_FAILURE
    }
}