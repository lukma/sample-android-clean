package com.lukma.clean.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lukma.clean.domain.common.UseCase
import kotlinx.coroutines.Job

class ResourceLiveData<Entity>(private val useCase: UseCase<Entity>) : MutableLiveData<Resource<Entity>>() {

    fun execute(params: Map<String, Any?> = emptyMap()): Job? {
        postValue(Resource(ResourceState.ON_REQUEST))
        return useCase.execute(
            params,
            { postValue(Resource(ResourceState.ON_SUCCESS, data = it)) },
            { postValue(Resource(ResourceState.ON_FAILURE, error = it)) }
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
