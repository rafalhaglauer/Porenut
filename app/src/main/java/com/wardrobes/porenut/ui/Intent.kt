package com.wardrobes.porenut.ui

import android.content.Intent

private const val KEY_RELATIVE_COMPOSITION_ID = "key-relative-composition-id"

var Intent.relativeCompositionId: Long
    get() = getLongExtra(KEY_RELATIVE_COMPOSITION_ID, -1)
    set(value) {
        putExtra(KEY_RELATIVE_COMPOSITION_ID, value)
    }