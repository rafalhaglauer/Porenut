package com.wardrobes.porenut.ui.vo

import android.content.Intent
import android.os.Bundle

private const val KEY_RELATIVE_COMPOSITION_ID = "key-relative-composition-id"
private const val KEY_RELATIVE_DRILLING_ID = "key-relative-drilling-id"
private const val KEY_WARDROBE_ID = "key-wardrobe-id"
private const val KEY_DRILLING_ID = "key-drilling-id"
private const val KEY_REQUEST_TYPE = "key-request-type"
private const val KEY_ELEMENT_ID = "key-element-id"

const val UNDEFINED_ID = -1L

var Intent.wardrobeId: Long
    get() = getLongExtra(KEY_WARDROBE_ID, UNDEFINED_ID)
    set(value) {
        putExtra(KEY_WARDROBE_ID, value)
    }

var Intent.elementId: Long
    get() = getLongExtra(KEY_ELEMENT_ID, UNDEFINED_ID)
    set(value) {
        putExtra(KEY_ELEMENT_ID, value)
    }

var Intent.requestType: RequestType?
    get() = getSerializableExtra(KEY_REQUEST_TYPE) as? RequestType
    set(value) {
        putExtra(KEY_REQUEST_TYPE, value)
    }

var Intent.drillingId: Long
    get() = getLongExtra(KEY_DRILLING_ID, UNDEFINED_ID)
    set(value) {
        putExtra(KEY_DRILLING_ID, value)
    }

var Intent.relativeCompositionId: Long
    get() = getLongExtra(KEY_RELATIVE_COMPOSITION_ID, UNDEFINED_ID)
    set(value) {
        putExtra(KEY_RELATIVE_COMPOSITION_ID, value)
    }

var Intent.relativeDrillingId: Long?
    get() = getLongExtra(
        KEY_RELATIVE_DRILLING_ID,
        UNDEFINED_ID
    ).let { if (it == UNDEFINED_ID) null else it }
    set(value) {
        putExtra(KEY_RELATIVE_DRILLING_ID, value)
    }

var Bundle.wardrobeId: Long?
    get() = getLong(KEY_WARDROBE_ID, UNDEFINED_ID)
    set(value) {
        putLong(KEY_WARDROBE_ID, value ?: UNDEFINED_ID)
    }

var Bundle.elementId: Long
    get() = getLong(KEY_ELEMENT_ID, UNDEFINED_ID)
    set(value) {
        putLong(KEY_ELEMENT_ID, value)
    }

var Bundle.drillingId: Long
    get() = getLong(KEY_DRILLING_ID, UNDEFINED_ID)
    set(value) {
        putLong(KEY_DRILLING_ID, value)
    }

var Bundle.relativeCompositionId: Long
    get() = getLong(KEY_RELATIVE_COMPOSITION_ID, UNDEFINED_ID)
    set(value) {
        putLong(KEY_RELATIVE_COMPOSITION_ID, value)
    }