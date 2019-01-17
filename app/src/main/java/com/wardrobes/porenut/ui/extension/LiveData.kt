package com.wardrobes.porenut.ui.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wardrobes.porenut.ui.vo.Event

fun <T> LiveData<T>.updateValue(value: T) {
    (this as? MutableLiveData)?.value = value
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, action: T.() -> Unit) {
    observe(lifecycleOwner, Observer {
        action(it)
    })
}

fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    observe(lifecycleOwner) {
        getContentIfNotHandled()?.also { action(it) }
    }
}