package com.wardrobes.porenut.ui.common

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.ui.extension.float
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.view_offset.view.*

class OffsetView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        inflate(R.layout.view_offset, true)
        context.obtainStyledAttributes(attrs, R.styleable.OffsetView).apply {
            layoutOffset.hint = getString(R.styleable.OffsetView_android_text)
        }.also { it.recycle() }
    }

    var offset: Offset
        get() = Offset(value, offsetReference)
        set(offset) {
            value = offset.value
            offsetReference = offset.reference
        }

    var value: Float
        get() = txtOffset.float()
        set(value) {
            txtOffset.setText(value.toString())
        }

    var offsetReference: Offset.Reference
        get() = when (spinnerOffsetReference.selectedItemPosition) {
            0 -> Offset.Reference.BEGIN
            else -> Offset.Reference.END
        }
        set(reference) {
            when (reference) {
                Offset.Reference.BEGIN -> spinnerOffsetReference.setSelection(0)
                Offset.Reference.END -> spinnerOffsetReference.setSelection(1)
            }
        }
}