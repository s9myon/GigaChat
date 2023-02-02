package com.shudss00.gigachat.presentation.extensions

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

val View.measuredWidthWithMargins: Int
    get() {
        val lp = this.layoutParams as ViewGroup.MarginLayoutParams
        return this.measuredWidth + lp.leftMargin + lp.rightMargin
    }

val View.measuredHeightWithMargins: Int
    get() {
        val lp = this.layoutParams as ViewGroup.MarginLayoutParams
        return this.measuredHeight + lp.topMargin + lp.bottomMargin
    }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}