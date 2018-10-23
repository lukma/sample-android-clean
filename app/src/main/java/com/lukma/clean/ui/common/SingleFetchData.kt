package com.lukma.clean.ui.common

import androidx.lifecycle.MutableLiveData
import io.reactivex.subscribers.DisposableSubscriber

class SingleFetchData<Entity, Params>(
        private val onRunning: (Params?, DisposableSubscriber<Entity>) -> Unit
) {
    val state = MutableLiveData<State>()
    val data = MutableLiveData<Entity>()
    val error = MutableLiveData<Throwable>()

    fun run(params: Params? = null) {
        onRunning(params, object : DisposableSubscriber<Entity>() {
            override fun onComplete() {}

            override fun onNext(t: Entity) {
                state.postValue(State.ON_SUCCESS)
                data.postValue(t)
            }

            override fun onError(t: Throwable?) {
                state.postValue(State.ON_FAILURE)
                error.postValue(t)
            }
        })
    }

    enum class State {
        ON_REQUEST,
        ON_SUCCESS,
        ON_FAILURE
    }
}