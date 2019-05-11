package com.lukma.clean.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lukma.clean.domain.common.base.BaseDeferredUseCase

class SingleLiveData<Entity>(private val useCase: BaseDeferredUseCase<Entity>) : MutableLiveData<Entity>() {
    fun execute(params: Map<String, Any?> = emptyMap()) = useCase.execute(
        params = params,
        onSuccess = { postValue(it) }
    )

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
