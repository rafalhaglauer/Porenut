package com.wardrobes.porenut.ui.element

import android.content.Intent
import com.wardrobes.porenut.domain.UNDEFINED_ID

private const val KEY_ELEMENT_ID = "key-element-id"


var Intent.elementId: Long
    get() = getLongExtra(KEY_ELEMENT_ID, UNDEFINED_ID)
    set(value) {
        putExtra(KEY_ELEMENT_ID, value)
    }