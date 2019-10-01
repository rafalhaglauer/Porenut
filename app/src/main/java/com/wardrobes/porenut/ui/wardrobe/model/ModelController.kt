package com.wardrobes.porenut.ui.wardrobe.model

import android.view.MotionEvent
import min3d.core.Object3dContainer

class ModelController(private val model: Object3dContainer) {
    private var previousX: Float = 0.toFloat()
    private var previousY: Float = 0.toFloat()

    fun handleEvent(event: MotionEvent): Boolean = when (event.action and MotionEvent.ACTION_MASK) {
        MotionEvent.ACTION_DOWN -> {
            previousX = event.x
            previousY = event.y
            true
        }
        MotionEvent.ACTION_MOVE -> {
            if (event.pointerCount == 1) {
                model.rotation()?.apply {
                    this.y += (event.x - previousX) / 4
                    this.x += (event.y - previousY) / 4
                }
                previousX = event.x
                previousY = event.y
            }
            true
        }
        else -> false
    }
}