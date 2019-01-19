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
                val x = event.x
                val y = event.y
                val dx = x - previousX
                val dy = y - previousY
                previousX = x
                previousY = y
                model.rotation()?.apply {
                    this.y += dx / 4
                    this.x += dy / 4
                }
            }
            true
        }
        else -> false
    }
}