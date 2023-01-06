package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginEnd
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.color.MaterialColors
import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.extensions.measuredHeightWithMargins
import com.shudss00.gigachat.presentation.extensions.measuredWidthWithMargins
import java.lang.Integer.max

class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        private const val RECT_CORNERS_RADIUS = 10f
        private const val DEFAULT_SENDER_NAME = ""
        private const val DEFAULT_MESSAGE_TEXT = ""
    }

    private val messageBounds = RectF()
    private val paint = Paint().apply {
        isAntiAlias = true
        color = MaterialColors.getColor(
            this@MessageView,
            com.google.android.material.R.attr.colorSurface
        )
    }
    private val avatarImageView: ImageView
    private val senderNameTextView: TextView
    private val messageTextTextView: TextView
    private val flexboxLayout: FlexboxLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.view_message, this, true)
        avatarImageView = getChildAt(0) as ImageView
        senderNameTextView = getChildAt(1) as TextView
        messageTextTextView = getChildAt(2) as TextView
        flexboxLayout = getChildAt(3) as FlexboxLayout
        context.withStyledAttributes(attrs, R.styleable.MessageView, defStyleAttr) {
            avatarImageView.setImageResource(
                getResourceId(
                    R.styleable.MessageView_senderAvatar,
                    R.drawable.ic_person_foreground)
            )
            senderNameTextView.text = getString(R.styleable.MessageView_senderName) ?: DEFAULT_SENDER_NAME
            messageTextTextView.text = getString(R.styleable.MessageView_messageText) ?: DEFAULT_MESSAGE_TEXT
        }
    }

    fun setMessageItem(message: MessageItem) {
        avatarImageView.load(message.avatar) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_person_foreground)
            fallback(R.drawable.ic_person_foreground)
        }
        senderNameTextView.text = message.username
        messageTextTextView.text = message.text
        flexboxLayout.removeAllViews()
        message.reactions.forEach { reaction ->
            val emojiView = EmojiView(flexboxLayout.context)
            emojiView.setReactionItem(reaction)
            flexboxLayout.addView(emojiView)
        }
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0

        measureChildWithMargins(avatarImageView,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed)
        widthUsed += avatarImageView.measuredWidthWithMargins

        measureChildWithMargins(senderNameTextView,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed)
        heightUsed += senderNameTextView.measuredHeightWithMargins

        measureChildWithMargins(messageTextTextView,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed)
        heightUsed += messageTextTextView.measuredHeightWithMargins

        measureChildWithMargins(flexboxLayout,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed)
        heightUsed += flexboxLayout.measuredHeightWithMargins

        widthUsed += max(
            senderNameTextView.measuredWidthWithMargins,
            messageTextTextView.measuredWidthWithMargins
        )

        setMeasuredDimension(
            resolveSize(widthUsed + paddingStart + paddingEnd, widthMeasureSpec),
            heightUsed + paddingTop + paddingBottom
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentTop = paddingTop
        var currentLeft = paddingLeft
        avatarImageView.layout(
            currentLeft,
            currentTop,
            currentLeft + avatarImageView.measuredWidth,
            currentTop + avatarImageView.measuredHeight
        )

        currentLeft += avatarImageView.measuredWidthWithMargins
        senderNameTextView.layout(
            currentLeft,
            currentTop,
            currentLeft + senderNameTextView.measuredWidth,
            currentTop + senderNameTextView.measuredHeight
        )

        currentTop += senderNameTextView.measuredHeight
        messageTextTextView.layout(
            currentLeft,
            currentTop,
            currentLeft + messageTextTextView.measuredWidth,
            currentTop + messageTextTextView.measuredHeight
        )

        currentTop += messageTextTextView.measuredHeightWithMargins
        flexboxLayout.layout(
            currentLeft,
            currentTop,
            currentLeft + flexboxLayout.measuredWidth,
            currentTop + flexboxLayout.measuredHeight
        )

        messageBounds.set(
            (avatarImageView.right + avatarImageView.marginEnd).toFloat(),
            senderNameTextView.top.toFloat(),
            max(senderNameTextView.right, messageTextTextView.right).toFloat(),
            messageTextTextView.bottom.toFloat()
        )
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.drawRoundRect(messageBounds, RECT_CORNERS_RADIUS, RECT_CORNERS_RADIUS, paint)
        super.dispatchDraw(canvas)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}