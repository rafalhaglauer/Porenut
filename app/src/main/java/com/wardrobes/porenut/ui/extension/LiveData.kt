package com.wardrobes.porenut.ui.extension

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

fun <T> LiveData<T>.updateValue(value: T) {
    (this as? MutableLiveData)?.value = value
}