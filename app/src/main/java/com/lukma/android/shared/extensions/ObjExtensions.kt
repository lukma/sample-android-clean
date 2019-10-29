package com.lukma.android.shared.extensions

import androidx.lifecycle.MutableLiveData
import com.lukma.android.domain.common.entity.Either
import com.lukma.android.presentation.common.entity.Resource

fun <V> Either<Throwable, V>.asResource() = when (this) {
    is Either.Error -> Resource.Failure(this.error)
    is Either.Value -> Resource.Success(this.value)
}

fun <V> Either<Throwable, V>.toResourceLiveData(liveData: MutableLiveData<Resource<V>>) {
    liveData.postValue(this.asResource())
}

fun <V> Either<Throwable, V>.toLiveData(liveData: MutableLiveData<V>) {
    if (this is Either.Value) liveData.postValue(this.value)
}
