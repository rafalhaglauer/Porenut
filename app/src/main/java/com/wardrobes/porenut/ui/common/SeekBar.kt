package com.wardrobes.porenut.ui.common

import android.widget.SeekBar

internal infix fun SeekBar.onProgressChanged(zoomUpdated: () -> Unit) {
    setOnSeekBarChangeListener(object : OnProgressChanged() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            zoomUpdated()
        }
    })
}

abstract class OnProgressChanged : SeekBar.OnSeekBarChangeListener {
    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }
}