package com.lukma.clean.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lukma.clean.domain.common.UseCase
import kotlinx.coroutines.Job

class SingleLiveData<Entity>(private val useCase: UseCase<Entity>) : MutableLiveData<Entity>() {

    fun execute(params: Map<String, Any?> = emptyMap()): Job? {
        return useCase.execute(
            params,
            { postValue(it) },
            { it.printStackTrace() }
        )
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in Entity>) {
        if (!hasObservers()) {
            super.observe(owner, Observer {
                if (it == null) return@Observer
                observer.onChanged(it)
                value = null
            })
        }
    }
}
