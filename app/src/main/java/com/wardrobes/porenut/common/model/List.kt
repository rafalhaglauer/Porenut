package com.wardrobes.porenut.common.model

fun <T> MutableList<T>.replace(items: List<T>) {
    removeAll { true }
    addAll(items)
}