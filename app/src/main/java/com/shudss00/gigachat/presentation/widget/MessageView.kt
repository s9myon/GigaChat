package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginEnd
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.color.MaterialColors
import com.shudss00.gigachat.R
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.presentation.extensions.*
import com.shudss00.gigachat.presentation.messenger.viewobject.MessageItem
import java.lang.Integer.max

private const val RECT_CORNERS_RADIUS = 20f

class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.messageViewStyle
) : ViewGroup(context, attrs, defStyleAttr) {

    interface MessageClickListener {
        fun onMessageLongClick(messageId: Long)
        fun onAddReactionButtonClick(messageId: Long)
        fun onReactionClick(messageId: Long, emoji: Emoji)
    }

    var messageClickListener: MessageClickListener? = null
    private var isOwnMessage = false
    private val messageBounds = RectF()
    private val ownMessagePaint = Paint().apply {
        isAntiAlias = true
        color = MaterialColors.getColor(
            this@MessageView,
            com.google.android.material.R.attr.colorPrimary
        )
    }
    private val nonOwnMessagePaint = Paint().apply {
        isAntiAlias = true
        color = MaterialColors.getColor(
            this@MessageView,
            com.google.android.material.R.attr.colorSurface
        )
    }
    private val avatarImageView: AppCompatImageView
    private val senderNameTextView: AppCompatTextView
    private val messageTextView: AppCompatTextView
    private val reactionFlexboxLayout: FlexboxLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.view_message, this, true)
        avatarImageView = getChildAt(0) as AppCompatImageView
        senderNameTextView = getChildAt(1) as AppCompatTextView
        messageTextView = getChildAt(2) as AppCompatTextView
        reactionFlexboxLayout = getChildAt(3) as FlexboxLayout
        context.withStyledAttributes(attrs, R.styleable.MessageView, defStyleAttr) {
            avatarImageView.setImageResource(
                getResourceId(
                    R.styleable.MessageView_senderAvatarSrc,
                    R.drawable.ic_person_foreground)
            )
            senderNameTextView.text = getString(R.styleable.MessageView_senderName).orEmpty()
            messageTextView.text = getString(R.styleable.MessageView_messageText).orEmpty()
        }
    }

    fun setMessageItem(message: MessageItem) {
        isOwnMessage = message.isOwnMessage
        if (isOwnMessage) {
            avatarImageView.hide()
            senderNameTextView.hide()
        } else {
            avatarImageView.show()
            avatarImageView.load(message.avatar) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_person_foreground)
                fallback(R.drawable.ic_person_foreground)
            }
            senderNameTextView.show()
            senderNameTextView.text = message.username.fromHtml()
        }
        messageTextView.text = message.text.fromHtml()
        messageTextView.setOnLongClickListener {
            messageClickListener?.onMessageLongClick(message.id)
            return@setOnLongClickListener true
        }
        setReactions(message)
        requestLayout()
        invalidate()
    }

    private fun setReactions(message: MessageItem) {
        reactionFlexboxLayout.removeAllViews()
        if (message.reactions.isNotEmpty()) {
            reactionFlexboxLayout.show()
            message.reactions.forEach { reaction ->
                val reactionView = ReactionView(reactionFlexboxLayout.context)
                reactionView.setReactionItem(reaction)
                reactionView.setOnClickListener {
                    messageClickListener?.onReactionClick(
                        messageId = message.id,
                        emoji = reaction.type
                    )
                }
                reactionFlexboxLayout.addView(reactionView)
            }
            LayoutInflater.from(reactionFlexboxLayout.context)
                .inflate(R.layout.view_add_reaction_button, reactionFlexboxLayout)
                .apply {
                    setOnClickListener {
                        messageClickListener?.onAddReactionButtonClick(message.id)
                    }
                }
        } else {
            reactionFlexboxLayout.hide()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var widthUsed = 0
        var heightUsed = 0

        if (!isOwnMessage) {
            measureChildWithMargins(avatarImageView,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
            widthUsed += avatarImageView.measuredWidthWithMargins

            measureChildWithMargins(senderNameTextView,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
            heightUsed += senderNameTextView.measuredHeightWithMargins
        }

        measureChildWithMargins(messageTextView,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed)
        heightUsed += messageTextView.measuredHeightWithMargins + 7.dpToPx()

        measureChildWithMargins(reactionFlexboxLayout,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed)
        heightUsed += reactionFlexboxLayout.measuredHeightWithMargins

        val heightSize = heightUsed + paddingTop + paddingBottom
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (isOwnMessage) {
            var currentTop = paddingTop

            messageTextView.layout(
                r - l - paddingRight - messageTextView.measuredWidth,
                currentTop,
                r - l - paddingRight,
                currentTop + messageTextView.measuredHeight
            )
            currentTop += messageTextView.measuredHeightWithMargins + 7.dpToPx()

            reactionFlexboxLayout.layout(
                r - l - paddingRight - reactionFlexboxLayout.measuredWidth,
                currentTop,
                r - l - paddingRight,
                currentTop + reactionFlexboxLayout.measuredHeight
            )

            messageBounds.set(
                messageTextView.left.toFloat(),
                messageTextView.top.toFloat(),
                messageTextView.right.toFloat(),
                messageTextView.bottom.toFloat()
            )
        } else {
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
            currentTop += senderNameTextView.measuredHeightWithMargins

            messageTextView.layout(
                currentLeft,
                currentTop,
                currentLeft + messageTextView.measuredWidth,
                currentTop + messageTextView.measuredHeight
            )
            currentTop += messageTextView.measuredHeightWithMargins + 7.dpToPx()

            reactionFlexboxLayout.layout(
                currentLeft,
                currentTop,
                currentLeft + reactionFlexboxLayout.measuredWidth,
                currentTop + reactionFlexboxLayout.measuredHeight
            )

            messageBounds.set(
                (avatarImageView.right + avatarImageView.marginEnd).toFloat(),
                senderNameTextView.top.toFloat(),
                max(senderNameTextView.right, messageTextView.right).toFloat(),
                messageTextView.bottom.toFloat()
            )
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (isOwnMessage) {
            canvas.drawRoundRect(messageBounds, RECT_CORNERS_RADIUS, RECT_CORNERS_RADIUS, ownMessagePaint)
        } else {
            canvas.drawRoundRect(messageBounds, RECT_CORNERS_RADIUS, RECT_CORNERS_RADIUS, nonOwnMessagePaint)
        }
        super.dispatchDraw(canvas)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}