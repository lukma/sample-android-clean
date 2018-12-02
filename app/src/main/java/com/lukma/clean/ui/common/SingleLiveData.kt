package com.lukma.clean.ui.common

import androidx.lifecycle.MutableLiveData
import io.reactivex.subscribers.DisposableSubscriber

class SingleLiveData<Entity, Params>(
    private val onRunning: (Params?, DisposableSubscriber<Entity>) -> Unit
) : MutableLiveData<SingleLiveData.Resource<Entity>>() {
    fun run(params: Params? = null) {
        value = Resource(State.ON_REQUEST)
        onRunning(params, object : DisposableSubscriber<Entity>() {
            override fun onComplete() {}

            override fun onNext(t: Entity) {
                value = Resource(State.ON_SUCCESS, data = t)
            }

            override fun onError(t: Throwable?) {
                value = Resource(State.ON_FAILURE, error = t)
            }
        })
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