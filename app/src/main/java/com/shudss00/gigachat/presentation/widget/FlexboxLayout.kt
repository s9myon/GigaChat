package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.lang.Integer.max

class FlexboxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

//    val verticalGap = 0
//    val horizontalGap = 0
//    init {
//        context.withStyledAttributes(attrs, R.styleable.CustomViewGroup, defStyleAttr) {
//            verticalOffset = getDimensionPixelOffset(R.styleable.CustomViewGroup_verticalOffset, 0)
//        }
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        } else {
            val firstChild = getChildAt(0)
            measureChild(firstChild, widthMeasureSpec)
            var currentWidth = firstChild.measuredWidth
            var heightUsed = firstChild.measuredHeight
            var widthUsed = currentWidth
            for (i in 1 until childCount) {
                val child = getChildAt(i)
                measureChild(child, widthMeasureSpec)
                if (currentWidth + child.measuredWidth < widthSize || widthMode == MeasureSpec.UNSPECIFIED) {
                    currentWidth += child.measuredWidth
                } else {
                    widthUsed = max(currentWidth, widthUsed)
                    currentWidth = child.measuredWidth
                    heightUsed += child.measuredHeight
                }
            }
            setMeasuredDimension(
                resolveSize(widthUsed + paddingStart + paddingEnd, widthMeasureSpec),
                resolveSize(heightUsed + paddingTop + paddingBottom, heightMeasureSpec)
            )
        }
    }

    private fun measureChild(child: View, parentWidthMeasureSpec: Int) {
        val childWidthMeasureSpec = when (MeasureSpec.getMode(parentWidthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> parentWidthMeasureSpec
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST ->
                MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(parentWidthMeasureSpec) - paddingStart - paddingEnd,
                    MeasureSpec.AT_MOST
                )
            else -> error("Unreachable")
        }
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        child.measure(
            childWidthMeasureSpec,
            childHeightMeasureSpec
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        var currentLeftBorder = paddingStart
        var currentTopBorder = paddingTop
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if ((currentLeftBorder + child.measuredWidth + paddingEnd) > width) {
                currentLeftBorder = paddingStart
                currentTopBorder += child.measuredHeight
            }
            child.layout(
                currentLeftBorder,
                currentTopBorder,
                currentLeftBorder + child.measuredWidth,
                currentTopBorder + child.measuredHeight
            )
            currentLeftBorder += child.measuredWidth
        }
    }
}