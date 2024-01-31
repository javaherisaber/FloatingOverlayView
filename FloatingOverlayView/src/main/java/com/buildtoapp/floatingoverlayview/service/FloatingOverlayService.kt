package com.buildtoapp.floatingoverlayview.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.ResultReceiver
import android.view.View
import com.buildtoapp.floatingoverlayview.component.FloatingComponent

class FloatingOverlayService : Service() {
    private var floatingComponent: FloatingComponent? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val layoutRes = intent.getIntExtra(EXTRA_LAYOUT_RESOURCE, -1)
        val xOffset = intent.getIntExtra(EXTRA_X_OFFSET, 0)
        val yOffset = intent.getIntExtra(EXTRA_Y_OFFSET, 0)
        val receiver = intent.getParcelableExtra<ResultReceiver>(EXTRA_RECEIVER)
        floatingComponent = FloatingComponent(layoutRes, this, xOffset, yOffset)
        if (receiver != null) {
            floatingComponent?.setReceiver(receiver)
        }
        floatingComponent?.setUp()
        view = floatingComponent?.floatingWindowModule?.getView()
        return START_STICKY_COMPATIBILITY
    }

    override fun onDestroy() {
        floatingComponent?.destroy()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_LAYOUT_RESOURCE = "extra_layout_resource"
        const val EXTRA_X_OFFSET = "extra_x_offset"
        const val EXTRA_Y_OFFSET = "extra_y_offset"
        const val EXTRA_RECEIVER = "extra_receiver"
        var view: View? = null
    }
}