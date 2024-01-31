package com.buildtoapp.floatingoverlayview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.annotation.LayoutRes
import com.buildtoapp.floatingoverlayview.callback.OnCloseListener
import com.buildtoapp.floatingoverlayview.callback.OnCreateListener
import com.buildtoapp.floatingoverlayview.receiver.FloatingResultReceiver
import com.buildtoapp.floatingoverlayview.service.FloatingOverlayService

class FloatingOverlayView(private val context: Context, @LayoutRes private val layoutRes: Int) {
    private var onCreateListener: OnCreateListener? = null
    private var onCloseListener: OnCloseListener? = null
    private var xOffset = 0
    private var yOffset = 0
    var isVisible = false
        private set

    fun setOnCreateListener(onCreateListener: OnCreateListener): FloatingOverlayView {
        this.onCreateListener = onCreateListener
        return this
    }

    fun setOnCloseListener(onCloseListener: OnCloseListener): FloatingOverlayView {
        this.onCloseListener = onCloseListener
        return this
    }

    fun setXOffset(xOffset: Int): FloatingOverlayView {
        this.xOffset = xOffset
        return this
    }

    fun setYOffset(yOffset: Int): FloatingOverlayView {
        this.yOffset = yOffset
        return this
    }

    fun create(): FloatingOverlayView? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.e(TAG, "Draw over other apps is not supported below Android 6")
            return null
        }
        if (!Settings.canDrawOverlays(context)) {
            throw IllegalStateException(
                """
                Cannot show overlay view without draw over other apps permission
                Use startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)) to grant the permission
                """
            )
        }
        isVisible = true
        val intent = getIntent(context)
        intent.putExtra(FloatingOverlayService.EXTRA_LAYOUT_RESOURCE, layoutRes)
        intent.putExtra(FloatingOverlayService.EXTRA_X_OFFSET, xOffset)
        intent.putExtra(FloatingOverlayService.EXTRA_Y_OFFSET, yOffset)
        stopService(context)
        if (onCreateListener != null || onCloseListener != null) {
            val handler = Handler(Looper.getMainLooper())
            val receiver = FloatingResultReceiver(this, onCreateListener, onCloseListener, handler)
            intent.putExtra(FloatingOverlayService.EXTRA_RECEIVER, receiver)
        }
        context.startService(intent)
        return this
    }

    fun destroy() {
        isVisible = false
        stopService(context)
    }

    companion object {
        private const val TAG = "FloatingOverlayView"
        private fun getIntent(context: Context) = Intent(context, FloatingOverlayService::class.java)

        @JvmStatic
        fun stopService(context: Context) {
            val intent = getIntent(context)
            context.stopService(intent)
        }
    }
}
