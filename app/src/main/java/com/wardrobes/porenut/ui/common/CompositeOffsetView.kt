package com.wardrobes.porenut.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.CompositeOffset
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.view_composite_offset.view.*

class CompositeOffsetView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        inflate(R.layout.view_composite_offset, true)
        context.obtainStyledAttributes(attrs, R.styleable.CompositeOffsetView).apply {
            txtTitle.text = getString(R.styleable.CompositeOffsetView_android_text)
        }.also { it.recycle() }
    }

    var compositeOffset: CompositeOffset
        get() = CompositeOffset(offset, percentageOffset)
        set(value) {
            offset = value.offset
            percentageOffset = value.percentageOffset
        }

    var offset: Offset
        get() = viewOffset.offset
        set(value) {
            viewOffset.offset = value
        }

    var percentageOffset: Offset
        get() = viewPercentageOffset.offset
        set(value) {
            viewPercentageOffset.offset = value
        }
}