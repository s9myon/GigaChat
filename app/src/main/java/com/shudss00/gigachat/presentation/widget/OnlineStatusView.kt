package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.common.OnlineStatus

class OnlineStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var text = ""
    private val textBounds = Rect()
    private val textCoordinate = PointF()
    private var textPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = resources.getDimension(R.dimen.onlineStatusView_textSize)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.OnlineStatusView, defStyleAttr) {
            setOnlineStatus(
                OnlineStatus.from(
                    getInt(R.styleable.OnlineStatusView_onlineStatus, 3)
                )
            )
            textPaint.textSize = getDimension(
                R.styleable.OnlineStatusView_android_textSize,
                resources.getDimension(R.dimen.onlineStatusView_textSize)
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        val sumWidth = paddingLeft + textBounds.width() + paddingRight
        val sumHeight = paddingTop + textBounds.height() + paddingBottom

        setMeasuredDimension(
            resolveSize(sumWidth, widthMeasureSpec),
            resolveSize(sumHeight, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        textCoordinate.x = w / 2 - textBounds.width() / 2f
        textCoordinate.y = h / 2 + textBounds.height() / 2f // - textPaint.descent()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, textCoordinate.x, textCoordinate.y, textPaint)
    }

    fun setOnlineStatus(status: OnlineStatus) {
        when (status) {
            OnlineStatus.ACTIVE -> {
                text = context.getString(R.string.textView_active)
                textPaint.color = ContextCompat.getColor(context, R.color.green_500)
            }
            OnlineStatus.IDLE -> {
                text = context.getString(R.string.textView_idle)
                textPaint.color = ContextCompat.getColor(context, R.color.orange)
            }
            OnlineStatus.OFFLINE -> {
                text = context.getString(R.string.textView_offline)
                textPaint.color = ContextCompat.getColor(context, R.color.red)
            }
            OnlineStatus.INDEFINITE -> {
                text = ""
            }
        }
        invalidate()
        requestLayout()
    }
}