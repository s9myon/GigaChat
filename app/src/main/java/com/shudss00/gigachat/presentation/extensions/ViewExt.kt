package com.shudss00.gigachat.presentation.extensions

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

fun View.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()
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

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}