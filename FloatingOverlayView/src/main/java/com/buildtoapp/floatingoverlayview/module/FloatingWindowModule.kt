package com.buildtoapp.floatingoverlayview.module

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes

internal class FloatingWindowModule(private val context: Context, @LayoutRes private val layoutRes: Int) {
    private var params: WindowManager.LayoutParams? = null
    private var _view: View? = null
    var windowManager: WindowManager? = null
        private set

    fun create() {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager?.addView(getView(), getParams())
    }

    fun getParams(): WindowManager.LayoutParams {
        if (params == null) {
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                windowType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }
        return params!!
    }

    fun getView(): View {
        if (_view == null) {
            _view = View.inflate(context, layoutRes, null)
        }
        return _view!!
    }

    private val windowType: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }

    fun destroy() {
        try {
            if (windowManager != null && _view != null) {
                windowManager?.removeViewImmediate(_view)
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            params = null
            _view = null
            windowManager = null
        }
    }
}
