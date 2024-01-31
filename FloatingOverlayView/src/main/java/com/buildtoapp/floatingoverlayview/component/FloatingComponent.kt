package com.buildtoapp.floatingoverlayview.component

import android.content.Context
import android.os.Bundle
import android.os.ResultReceiver
import android.view.View
import com.buildtoapp.floatingoverlayview.module.FloatingViewMovementModule
import com.buildtoapp.floatingoverlayview.module.FloatingWindowModule

internal class FloatingComponent(
    private val layoutRes: Int,
    private val context: Context,
    private val xOffset: Int,
    private val yOffset: Int
) {
    private var receiver: ResultReceiver? = null
    var floatingWindowModule: FloatingWindowModule? = null
        private set
    private var floatingViewMovementModule: FloatingViewMovementModule? = null

    fun setUp() {
        floatingWindowModule = FloatingWindowModule(context, layoutRes)
        floatingWindowModule?.create()
        val floatingView = floatingWindowModule?.getView()
        val rootContainer = floatingView?.findViewById<View>(viewRootId)
        floatingViewMovementModule = FloatingViewMovementModule(
            floatingWindowModule?.getParams(),
            rootContainer,
            floatingWindowModule?.windowManager,
            floatingView,
            xOffset,
            yOffset
        )
        floatingViewMovementModule?.run()
        sendAction(ACTION_ON_CREATE, Bundle())
    }

    fun setReceiver(receiver: ResultReceiver?) {
        this.receiver = receiver
    }

    private val viewRootId: Int
        get() = context.resources.getIdentifier("root_container", "id", context.packageName)

    private fun sendAction(action: Int, bundle: Bundle) {
        receiver?.send(action, bundle)
    }

    fun destroy() {
        sendAction(ACTION_ON_CLOSE, Bundle())
        floatingWindowModule?.destroy()
        floatingViewMovementModule?.destroy()
        floatingWindowModule = null
        floatingViewMovementModule = null
    }

    companion object {
        const val ACTION_ON_CREATE = 0x0002
        const val ACTION_ON_CLOSE = 0x00001
    }
}
