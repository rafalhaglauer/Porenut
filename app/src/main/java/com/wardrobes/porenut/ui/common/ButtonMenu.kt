package com.wardrobes.porenut.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate

class ButtonMenu(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        inflate(R.layout.button_menu, attachToRoot = true)

    }
}