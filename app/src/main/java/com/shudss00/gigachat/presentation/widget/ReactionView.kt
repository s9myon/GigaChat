package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.google.android.material.color.MaterialColors
import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.model.Reaction

class ReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.reactionViewStyle
) : View(context, attrs, defStyleAttr) {

    private var text = ""
    private val textBounds = Rect()
    private val textCoordinate = PointF()
    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        color = MaterialColors.getColor(
            this@ReactionView,
            com.google.android.material.R.attr.colorOnSurfaceVariant
        )
        textSize = resources.getDimension(R.dimen.reactionView_textSize)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.ReactionView, defStyleAttr) {
            setText(
                emojiType = getString(R.styleable.ReactionView_emojiType).orEmpty(),
                reactionsNumber = getInteger(R.styleable.ReactionView_reactionsNumber, 0)
            )
        }
    }

    private fun setText(emojiType: String, reactionsNumber: Int) {
        text = "$emojiType $reactionsNumber"
    }

    fun setReactionItem(reaction: Reaction) {
        setText(
            emojiType = reaction.type.unicode,
            reactionsNumber = reaction.reactionNumber
        )
        this.isSelected = reaction.isSelected
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        val sumWidth = paddingLeft + textBounds.width() + paddingRight
        val sumHeight = paddingTop + textBounds.height() + paddingBottom
        val resultWidth = resolveSize(sumWidth, widthMeasureSpec)
        val resultHeight = resolveSize(sumHeight, heightMeasureSpec)

        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        textCoordinate.x = w / 2 - textBounds.width() / 2f
        textCoordinate.y = h / 2 + textBounds.height() / 2f - textPaint.descent()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, textCoordinate.x, textCoordinate.y, textPaint)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    companion object {
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }
}