package com.wardrobes.porenut.api.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.fetchStateFullModel(onSuccess: (T) -> Unit, onError: (String) -> Unit, onLoading: () -> Unit): Disposable =
        applySchedulers()
                .doOnSubscribe { onLoading() }
                .subscribe({ onSuccess(it) }, {
                    it.printStackTrace()
                    onError(it.localizedMessage ?: "Wystąpił błąd!")
                })


fun <T> Observable<T>.applySchedulers(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
