package com.wardrobes.porenut.ui.wardrobe.manage

import android.content.Intent
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.wardrobe.RequestType

private const val KEY_WARDROBE_CREATION_TYPE = "key-wardrobe-creation-type"
private const val KEY_WARDROBE_ID = "key-wardrobe-id"
private const val KEY_REQUEST_TYPE = "key-request-type"

var Intent.creationType: Wardrobe.CreationType?
    get() = getSerializableExtra(KEY_WARDROBE_CREATION_TYPE) as? Wardrobe.CreationType
    set(value) {
        putExtra(KEY_WARDROBE_CREATION_TYPE, value)
    }

var Intent.wardrobeId: Long
    get() = getLongExtra(KEY_WARDROBE_ID, UNDEFINED_ID)
    set(value) {
        putExtra(KEY_WARDROBE_ID, value)
    }

var Intent.requestType: RequestType?
    get() = getSerializableExtra(KEY_REQUEST_TYPE) as? RequestType
    set(value) {
        putExtra(KEY_REQUEST_TYPE, value)
    }