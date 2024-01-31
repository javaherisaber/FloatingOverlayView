package com.buildtoapp.floatingoverlayview.receiver

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import com.buildtoapp.floatingoverlayview.FloatingOverlayView
import com.buildtoapp.floatingoverlayview.callback.OnCloseListener
import com.buildtoapp.floatingoverlayview.callback.OnCreateListener
import com.buildtoapp.floatingoverlayview.component.FloatingComponent
import com.buildtoapp.floatingoverlayview.service.FloatingOverlayService

internal class FloatingResultReceiver(
    private val layout: FloatingOverlayView,
    private val onCreateListener: OnCreateListener?,
    private val onCloseListener: OnCloseListener?,
    handler: Handler?,
) : ResultReceiver(handler) {
    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        if (resultCode == FloatingComponent.ACTION_ON_CREATE && FloatingOverlayService.view != null) {
            onCreateListener?.onCreate(layout, FloatingOverlayService.view!!)
        }
        if (resultCode == FloatingComponent.ACTION_ON_CLOSE) {
            onCloseListener?.onClose()
        }
        super.onReceiveResult(resultCode, resultData)
    }
}
