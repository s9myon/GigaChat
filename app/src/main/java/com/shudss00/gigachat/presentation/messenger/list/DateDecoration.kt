package com.shudss00.gigachat.presentation.messenger.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.presentation.extensions.dpToPx

class DateDecoration : RecyclerView.ItemDecoration() {

    private val commonPadding = 10.dpToPx()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            left = commonPadding
            right = commonPadding
            top = commonPadding
            bottom = commonPadding
        }
    }
}