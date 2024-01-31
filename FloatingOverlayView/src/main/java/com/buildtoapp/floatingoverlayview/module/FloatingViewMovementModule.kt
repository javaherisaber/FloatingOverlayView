package com.buildtoapp.floatingoverlayview.module

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

internal class FloatingViewMovementModule(
    private var params: WindowManager.LayoutParams?,
    private val rootContainer: View?,
    private var windowManager: WindowManager?,
    private var baseView: View?,
    private val xOffset: Int,
    private val yOffset: Int
) {
    fun run() {
        if (rootContainer != null) {
            if (xOffset != 0) {
                params?.x = xOffset
            }
            if (xOffset != 0) {
                params?.y = yOffset
            }
            if (xOffset != 0 || yOffset != 0) {
                windowManager?.updateViewLayout(baseView, params)
            }
            rootContainer.setOnTouchListener(onTouchListener)
        }
    }

    private val onTouchListener = object : View.OnTouchListener {
        private var initialX = 0
        private var initialY = 0
        private var initialTouchX = 0f
        private var initialTouchY = 0f

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    //remember the initial position.
                    initialX = params!!.x
                    initialY = params!!.y
                    //get the touch location
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    return true
                }

                MotionEvent.ACTION_MOVE -> {
                    //Calculate the X and Y coordinates of the view.
                    params!!.x = (initialX + (event.rawX - initialTouchX)).toInt()
                    params!!.y = (initialY + (event.rawY - initialTouchY)).toInt()
                    //Update the layout with new X & Y coordinate
                    windowManager!!.updateViewLayout(baseView, params)
                    return true
                }
            }
            return false
        }
    }

    fun destroy() {
        try {
            if (baseView != null) {
                windowManager?.removeViewImmediate(baseView)
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            params = null
            baseView = null
            windowManager = null
        }
    }
}
