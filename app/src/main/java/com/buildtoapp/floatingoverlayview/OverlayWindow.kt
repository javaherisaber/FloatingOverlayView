package com.buildtoapp.floatingoverlayview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView

class OverlayWindow(private val context: Context) {
    fun showStopOverlay() {
        if (Settings.canDrawOverlays(context)) {
            showOverlayView()
        } else {
            openSettingsScreen()
        }
    }

    private fun showOverlayView() {
        FloatingOverlayView(context, R.layout.view_overlay)
            .setXOffset(800)
            .setOnCreateListener { layout: FloatingOverlayView, view: View ->
                val closeButton = view.findViewById<View>(R.id.window_close)
                val markerButton = view.findViewById<View>(R.id.markerButton)
                val titleView = view.findViewById<TextView>(R.id.titleText)
                closeButton.setOnClickListener { layout.destroy() }
                markerButton.setOnClickListener {
                    flipVisibility(titleView)
                    flipVisibility(closeButton)
                }
                titleView.setOnClickListener { v: View -> openMainScreen(v) }
            }
            .create()
    }

    private fun openMainScreen(view: View) {
        val intent = Intent(view.context, MainActivity::class.java)
        val bundle = Bundle()
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    private fun openSettingsScreen() {
        val uri = Uri.parse("package:" + context.packageName)
        val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri)
        context.startActivity(myIntent)
    }

    private fun flipVisibility(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}