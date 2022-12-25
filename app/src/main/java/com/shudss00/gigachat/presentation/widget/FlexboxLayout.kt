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
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        } else {
            val firstChild = getChildAt(0)
            measureChild(firstChild, widthMeasureSpec)
            var currentWidth = paddingStart + firstChild.measuredWidth
            var heightUsed = firstChild.measuredHeight
            var widthUsed = currentWidth
            for (i in 1 until childCount) {
                val child = getChildAt(i)
                measureChild(child, widthMeasureSpec)
                if (currentWidth + child.measuredWidth + paddingEnd < widthSize || widthMode == MeasureSpec.UNSPECIFIED) {
                    currentWidth += child.measuredWidth
                } else {
                    widthUsed = max(currentWidth + paddingEnd, widthUsed)
                    currentWidth = paddingStart + firstChild.measuredWidth
                    heightUsed += child.measuredHeight
                }
            }
            setMeasuredDimension(
                resolveSize(widthUsed, widthMeasureSpec),
                resolveSize(paddingTop + heightUsed + paddingBottom, heightMeasureSpec)
            )
        }
    }

    private fun measureChild(child: View, parentWidthMeasureSpec: Int) {
        val childWidthMeasureSpec = when (MeasureSpec.getMode(parentWidthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> parentWidthMeasureSpec
            MeasureSpec.AT_MOST -> parentWidthMeasureSpec
            MeasureSpec.EXACTLY -> MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(parentWidthMeasureSpec),
                MeasureSpec.AT_MOST
            )
            else -> error("Unreachable")
        }
        child.measure(
            childWidthMeasureSpec,
            MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(parentWidthMeasureSpec), MeasureSpec.AT_MOST)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeftBorder = paddingStart
        var currentTopBorder = paddingTop
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if ((currentLeftBorder + child.measuredWidth) > (r - l)) {
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