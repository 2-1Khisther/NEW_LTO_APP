package com.example.new_lto_app

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Android 15+ draws every app edge-to-edge. Use this instead of fitsSystemWindows
// when a surface should run under a system bar (e.g. a bottom sheet under the
// gesture bar) but keep its content clear of it. Adds the bar height on top of
// the view's existing XML padding.
object InsetsHelper {

    fun padForSystemBars(view: View, top: Boolean = false, bottom: Boolean = false) {
        val start = view.paddingTop
        val end = view.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft,
                if (top) start + bars.top else start,
                v.paddingRight,
                if (bottom) end + bars.bottom else end
            )
            insets
        }
    }
}
