package com.wardrobes.porenut.api

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Flowable<T>.fetchStateFullModel(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, onLoading: () -> Unit) =
        apply {
            applySchedulers()
            doOnSubscribe { onLoading() }
            subscribe({ onSuccess(it) }, { onError(it) })
        }

fun <T> Flowable<T>.applySchedulers(): Flowable<T> =
        apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
        }
