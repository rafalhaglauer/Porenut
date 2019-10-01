package com.wardrobes.porenut.ui.common

class Event<T>(private val item: T) {
    private var isHandled = false

    fun getContentIfNotHandled(): T? {
        if (!isHandled) {
            isHandled = true
            return item
        }
        return null
    }
}