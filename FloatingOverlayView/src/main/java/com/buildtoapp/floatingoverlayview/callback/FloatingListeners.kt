package com.buildtoapp.floatingoverlayview.callback

import android.view.View
import com.buildtoapp.floatingoverlayview.FloatingOverlayView

fun interface OnCreateListener {
    fun onCreate(layout: FloatingOverlayView, view: View)
}

fun interface OnCloseListener {
    fun onClose()
}