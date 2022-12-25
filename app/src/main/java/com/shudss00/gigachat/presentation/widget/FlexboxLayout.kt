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
                if (currentWidth + horizontalGap + child.measuredWidth < widthSize || widthMode == MeasureSpec.UNSPECIFIED) {
                    currentWidth += horizontalGap + child.measuredWidth
                } else {
                    widthUsed = max(currentWidth, widthUsed)
                    currentWidth = child.measuredWidth
                    heightUsed += child.measuredHeight + verticalGap
                }
            }
            setMeasuredDimension(
                resolveSize(widthUsed + paddingStart + paddingEnd, widthMeasureSpec),
                resolveSize(heightUsed + paddingTop + paddingBottom, heightMeasureSpec)
            )
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        var currentLeftBorder = paddingStart
        var currentTopBorder = paddingTop
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if ((currentLeftBorder + child.measuredWidth + paddingEnd) > width) {
                currentLeftBorder = paddingStart
                currentTopBorder += child.measuredHeight + verticalGap
            }
            child.layout(
                currentLeftBorder,
                currentTopBorder,
                currentLeftBorder + child.measuredWidth,
                currentTopBorder + child.measuredHeight
            )
            currentLeftBorder += child.measuredWidth + horizontalGap
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