package com.lukma.clean.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lukma.clean.domain.common.UseCase

class SingleLiveData<Entity>(private val useCase: UseCase<*>) : MutableLiveData<Entity>() {
    @Suppress("UNCHECKED_CAST")
    fun execute(
        params: Map<String, Any?> = emptyMap(),
        transformer: (Any?) -> Entity = { it as Entity }
    ) = useCase.execute(
        params = params,
        onSuccess = { postValue(transformer(it)) }
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
