package com.lukma.clean.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lukma.clean.domain.common.base.BaseDeferredUseCase
import kotlinx.coroutines.Job

class ResourceLiveData<Entity>(private val useCase: BaseDeferredUseCase<Entity>) :
    MutableLiveData<Resource<Entity>>() {

    fun execute(params: Map<String, Any?> = emptyMap()): Job {
        postValue(Resource(State.ON_REQUEST))
        return useCase.execute(
            params = params,
            onSuccess = { postValue(Resource(State.ON_SUCCESS, data = it)) },
            onError = { postValue(Resource(State.ON_FAILURE, error = it)) }
        )
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in Resource<Entity>>) {
        if (!hasObservers()) {
            super.observe(owner, Observer {
                if (it == null) return@Observer
                observer.onChanged(it)
                value = null
            })
        }
    }
}
