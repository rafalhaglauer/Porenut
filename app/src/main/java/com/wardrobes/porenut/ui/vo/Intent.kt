package com.wardrobes.porenut.ui.vo

import android.content.Intent
import android.os.Bundle
import com.wardrobes.porenut.domain.Wardrobe

private const val KEY_RELATIVE_COMPOSITION_ID = "key-relative-composition-id"
private const val KEY_WARDROBE_CREATION_TYPE = "key-wardrobe-creation-type"
private const val KEY_WARDROBE_ID = "key-wardrobe-id"
private const val KEY_DRILLING_ID = "key-drilling-id"
private const val KEY_REQUEST_TYPE = "key-request-type"
private const val KEY_ELEMENT_ID = "key-element-id"

const val UNDEFINED_ID = -1L

var Intent.wardrobeCreationType: Wardrobe.CreationType?
    get() = getSerializableExtra(KEY_WARDROBE_CREATION_TYPE) as? Wardrobe.CreationType
    set(value) {
        putExtra(KEY_WARDROBE_CREATION_TYPE, value)
    }

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

var Bundle.wardrobeCreationType: Wardrobe.CreationType?
    get() = getSerializable(KEY_WARDROBE_CREATION_TYPE) as? Wardrobe.CreationType
    set(value) {
        putSerializable(KEY_WARDROBE_CREATION_TYPE, value)
    }

var Bundle.wardrobeId: Long
    get() = getLong(KEY_WARDROBE_ID, UNDEFINED_ID)
    set(value) {
        putLong(KEY_WARDROBE_ID, value)
    }

var Bundle.elementId: Long
    get() = getLong(KEY_ELEMENT_ID, UNDEFINED_ID)
    set(value) {
        putLong(KEY_ELEMENT_ID, value)
    }

var Bundle.requestType: RequestType?
    get() = getSerializable(KEY_REQUEST_TYPE) as? RequestType
    set(value) {
        putSerializable(KEY_REQUEST_TYPE, value)
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