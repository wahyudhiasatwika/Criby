package com.developer.rozan.criby.utils

import android.view.View
import androidx.core.view.isVisible

fun View.visible() {
    if (!this.isVisible)
        this.visibility = View.VISIBLE
}

fun View.gone() {
    if (this.isVisible)
        this.visibility = View.GONE
}

fun View.enable() {
    if (!this.isEnabled)
        this.isEnabled = true
}

fun View.disable() {
    if (this.isEnabled)
        this.isEnabled = false
}
