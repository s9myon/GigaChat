package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import com.shudss00.gigachat.R
import java.lang.Integer.max
import kotlin.properties.Delegates

class FlexboxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.flexboxLayoutStyle
) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_VERTICAL_GAP = 0
        const val DEFAULT_HORIZONTAL_GAP = 0
    }

    private var verticalGap: Int by Delegates.notNull()
    private var horizontalGap: Int by Delegates.notNull()
    init {
        context.withStyledAttributes(attrs, R.styleable.FlexboxLayout, defStyleAttr) {
            verticalGap = getDimensionPixelOffset(R.styleable.FlexboxLayout_verticalGap, DEFAULT_VERTICAL_GAP)
            horizontalGap = getDimensionPixelOffset(R.styleable.FlexboxLayout_horizontalGap, DEFAULT_HORIZONTAL_GAP)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else {
            var isFirstRow = true
            val firstChild = getChildAt(0)
            measureChild(firstChild, widthMeasureSpec)
            var currentWidth = firstChild.measuredWidth
            var maxWidth = 0
            var currentMaxHeight = firstChild.measuredHeight
            var usedHeight = 0
            for (i in 1 until childCount) {
                val child = getChildAt(i)
                measureChild(child, widthMeasureSpec)
                if (currentWidth + horizontalGap + child.measuredWidth < widthSize || widthMode == MeasureSpec.UNSPECIFIED) {
                    currentWidth += horizontalGap + child.measuredWidth
                    currentMaxHeight = max(child.measuredHeight, currentMaxHeight)
                } else if (isFirstRow) {
                    maxWidth = max(currentWidth, maxWidth)
                    currentWidth = child.measuredWidth
                    usedHeight += currentMaxHeight
                    currentMaxHeight = child.measuredHeight
                    isFirstRow = false
                } else {
                    maxWidth = max(currentWidth, maxWidth)
                    currentWidth = child.measuredWidth
                    usedHeight += verticalGap + currentMaxHeight
                    currentMaxHeight = child.measuredHeight
                }
            }
            maxWidth = max(currentWidth, maxWidth)
            if (isFirstRow) {
                usedHeight = currentMaxHeight
            } else {
                usedHeight += verticalGap + currentMaxHeight
            }
            setMeasuredDimension(
                resolveSize(maxWidth + paddingStart + paddingEnd, widthMeasureSpec),
                resolveSize(usedHeight + paddingTop + paddingBottom, heightMeasureSpec)
            )
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        var currentTop = paddingTop
        var currentLeft = paddingLeft
        var currentMaxHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if ((currentLeft + child.measuredWidth + paddingRight) > width) {
                currentLeft = paddingLeft
                currentTop += currentMaxHeight + verticalGap
                currentMaxHeight = 0
            }
            child.layout(
                currentLeft,
                currentTop,
                currentLeft + child.measuredWidth,
                currentTop + child.measuredHeight
            )
            currentLeft += child.measuredWidth + horizontalGap
            currentMaxHeight = max(currentMaxHeight, child.measuredHeight)
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
}