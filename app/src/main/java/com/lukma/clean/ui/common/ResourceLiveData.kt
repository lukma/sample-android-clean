package com.lukma.clean.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lukma.clean.domain.common.UseCase
import kotlinx.coroutines.Job

class ResourceLiveData<Entity>(
    private val useCase: UseCase<*>
) : MutableLiveData<ResourceLiveData.Resource<Entity>>() {
    @Suppress("UNCHECKED_CAST")
    fun execute(
        params: Map<String, Any?> = emptyMap(),
        transformer: (Any?) -> Entity = { it as Entity }
    ): Job? {
        postValue(Resource(State.ON_REQUEST))
        return useCase.execute(
            params = params,
            onSuccess = { postValue(Resource(State.ON_SUCCESS, data = transformer(it))) },
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
