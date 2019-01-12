package com.wardrobes.porenut.ui.viewer.view

import android.content.Context
import android.graphics.PointF
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.wardrobes.porenut.ui.viewer.model.Model

private const val TOUCH_NONE = 0
private const val TOUCH_ROTATE = 1
private const val TOUCH_ZOOM = 2

class ModelSurfaceView(context: Context, model: Model) : GLSurfaceView(context) {

    private val renderer: ModelRenderer

    private var previousX: Float = 0.toFloat()
    private var previousY: Float = 0.toFloat()

    private val pinchStartPoint = PointF()
    private var pinchStartDistance = 0.0f
    private var touchMode = TOUCH_NONE

    init {
        setEGLContextClientVersion(2)
        renderer = ModelRenderer(model)
        setRenderer(renderer)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                previousX = event.x
                previousY = event.y
                true
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    if (touchMode != TOUCH_ROTATE) {
                        previousX = event.x
                        previousY = event.y
                    }
                    touchMode = TOUCH_ROTATE
                    val x = event.x
                    val y = event.y
                    val dx = x - previousX
                    val dy = y - previousY
                    previousX = x
                    previousY = y
                    renderer.rotate(dy.toDp(), dx.toDp())
                } else if (event.pointerCount == 2) {
                    if (touchMode != TOUCH_ZOOM) {
                        pinchStartDistance = getPinchDistance(event)
                        getPinchCenterPoint(event, pinchStartPoint)
                        previousX = pinchStartPoint.x
                        previousY = pinchStartPoint.y
                        touchMode = TOUCH_ZOOM
                    } else {
                        val pt = PointF()
                        getPinchCenterPoint(event, pt)
                        val dx = pt.x - previousX
                        val dy = pt.y - previousY
                        previousX = pt.x
                        previousY = pt.y
                        val pinchScale = getPinchDistance(event) / pinchStartDistance
                        pinchStartDistance = getPinchDistance(event)
                        renderer.translate(dx.toDp(), dy.toDp(), pinchScale)
                    }

                }
                requestRender()
                true
            }

            MotionEvent.ACTION_UP -> {
                pinchStartPoint.x = 0.0f
                pinchStartPoint.y = 0.0f
                touchMode = TOUCH_NONE
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private fun getPinchDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun getPinchCenterPoint(event: MotionEvent, pt: PointF) {
        pt.x = (event.getX(0) + event.getX(1)) * 0.5f
        pt.y = (event.getY(0) + event.getY(1)) * 0.5f
    }

    private fun Float.toDp(): Float = this / densityScalar

    private val densityScalar: Float
        get() = resources.displayMetrics.density

}