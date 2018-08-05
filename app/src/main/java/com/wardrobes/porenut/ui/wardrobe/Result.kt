package com.wardrobes.porenut.ui.wardrobe

enum class Result(val value: Int) {
    UNDEFINED(0), DELETED(1), MODIFIED(2), ADDED(3), COPIED(4);

    companion object {

        fun from(value: Int) = Result.values().firstOrNull { it.value == value } ?: UNDEFINED
    }
}