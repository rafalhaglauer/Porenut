package com.wardrobes.porenut.ui.vo

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