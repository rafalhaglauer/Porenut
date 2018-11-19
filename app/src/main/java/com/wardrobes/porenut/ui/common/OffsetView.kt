package com.wardrobes.porenut.ui.common

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SeekBar
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.ui.extension.float
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.string
import kotlinx.android.synthetic.main.view_offset.view.*
import java.util.*
import kotlin.math.roundToInt

class OffsetView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var timer: Timer? = null

    init {
        inflate(R.layout.view_offset, true)
        context.obtainStyledAttributes(attrs, R.styleable.OffsetView).apply {
            txtTitle.text = getString(R.styleable.OffsetView_android_text)
            recycle()
        }
        txtReferencePointProgress.setText(0.format())
        seekReferencePoint.seekReferencePoint.max = 10000
        setupTextWatcher()
        setOnSeekChangedListener()
        seekReferencePoint.progress = 0
    }

    var offset: Offset
        get() = Offset(value, referencePoint, direction)
        set(offset) {
            value = offset.value
            referencePoint = offset.percentageValue
            direction = offset.direction
        }

    private var value: Float
        get() = txtOffset.float()
        set(value) {
            txtOffset.setText(value.toString())
        }

    private var referencePoint: Float
        get() = txtReferencePointProgress.float()
        set(value) {
            txtReferencePointProgress.setText(value.toString())
        }

    private var direction: Offset.Direction
        get() = if (spinnerOffsetReference.selectedItemPosition == 0) Offset.Direction.FORWARD else Offset.Direction.BACKWARD
        set(value) {
            when (value) {
                Offset.Direction.FORWARD -> spinnerOffsetReference.setSelection(0)
                Offset.Direction.BACKWARD -> spinnerOffsetReference.setSelection(1)
            }
        }

    private fun setupTextWatcher() {
        txtReferencePointProgress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        (context as? Activity)?.runOnUiThread {
                            txtReferencePointProgress.float().also {
                                val current = if (it > 1F) 1F else it
                                seekReferencePoint.progress = (current * 10000).roundToInt()
                                val formatted = current.format()
                                val oldSelection = txtReferencePointProgress.selectionStart
                                if (txtReferencePointProgress.string() != formatted) {
                                    txtReferencePointProgress.setText(formatted)
                                    txtReferencePointProgress.setSelection(oldSelection)
                                }
                            }
                        }
                    }

                }, 1000)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

        })
    }

    private fun setOnSeekChangedListener() {
        seekReferencePoint.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                isUserInteraction: Boolean
            ) {
                if (isUserInteraction) txtReferencePointProgress.setText(((progress / 100) * 100 / 10000f).format())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun Int.format() = String.format(Locale.US, "%.4f", toFloat())

    private fun Float.format() = String.format(Locale.US, "%.4f", this)

}