package com.shudss00.gigachat.presentation.userlist.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.presentation.extensions.dpToPx

class UserItemDecorator : RecyclerView.ItemDecoration() {

    private val extremeVerticalPadding = 25.dpToPx()
    private val verticalPadding = 17.dpToPx()
    private val horizontalPadding = 15.dpToPx()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val itemCount = state.itemCount
        when {
            // first elem of list
            itemPosition == 0 ->
                outRect.apply {
                    left = horizontalPadding
                    right = horizontalPadding
                    top = extremeVerticalPadding
                    bottom = verticalPadding
                }
            // last elem of list
            itemCount > 0 && itemPosition == itemCount - 1 ->
                outRect.apply {
                    left = horizontalPadding
                    right = horizontalPadding
                    top = verticalPadding
                    bottom = extremeVerticalPadding
                }
            else ->
                outRect.apply {
                    left = horizontalPadding
                    right = horizontalPadding
                    top = verticalPadding
                    bottom = verticalPadding
                }
        }
    }
}