package com.shudss00.gigachat.presentation.extensions

import android.view.View
import android.view.ViewGroup

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

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}