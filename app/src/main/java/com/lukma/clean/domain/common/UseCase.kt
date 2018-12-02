package com.lukma.clean.domain.common

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

abstract class UseCase<Entity, in Params> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun build(params: Params): Flowable<Entity>

    fun execute(params: Params, subscriber: DisposableSubscriber<Entity>) {
        val disposable = build(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(subscriber)
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}