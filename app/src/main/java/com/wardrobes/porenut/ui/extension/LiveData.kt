package com.wardrobes.porenut.ui.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.updateValue(value: T) {
    (this as? MutableLiveData)?.value = value
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, action: T.() -> Unit) {
    observe(lifecycleOwner, Observer {
        action(it)
    })
}