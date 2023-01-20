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
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.extensions.*
import java.lang.Integer.max



private const val RECT_CORNERS_RADIUS = 20f
private const val DEFAULT_SENDER_NAME = ""
private const val DEFAULT_MESSAGE_TEXT = ""

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
    private val avatarImageView: ImageView
    private val senderNameTextView: TextView
    private val messageTextTextView: TextView
    private val reactionFlexboxLayout: FlexboxLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.view_message, this, true)
        avatarImageView = getChildAt(0) as ImageView
        senderNameTextView = getChildAt(1) as TextView
        messageTextTextView = getChildAt(2) as TextView
        reactionFlexboxLayout = getChildAt(3) as FlexboxLayout
        context.withStyledAttributes(attrs, R.styleable.MessageView, defStyleAttr) {
            avatarImageView.setImageResource(
                getResourceId(
                    R.styleable.MessageView_senderAvatarSrc,
                    R.drawable.ic_person_foreground)
            )
            senderNameTextView.text = getString(R.styleable.MessageView_senderName) ?: DEFAULT_SENDER_NAME
            messageTextTextView.text = getString(R.styleable.MessageView_messageText) ?: DEFAULT_MESSAGE_TEXT
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
            senderNameTextView.text = message.username
        }
        messageTextTextView.text = message.text//.fromHtml()
        messageTextTextView.setOnLongClickListener {
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

        if (isOwnMessage) {
            measureChildWithMargins(messageTextTextView,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
            heightUsed += messageTextTextView.measuredHeightWithMargins

            measureChildWithMargins(reactionFlexboxLayout,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
            heightUsed += reactionFlexboxLayout.measuredHeightWithMargins
        } else {
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

            measureChildWithMargins(reactionFlexboxLayout,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
            heightUsed += reactionFlexboxLayout.measuredHeightWithMargins
        }

        val heightSize = heightUsed + paddingTop + paddingBottom
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (isOwnMessage) {
            var currentTop = paddingTop

            messageTextTextView.layout(
                r - l - paddingRight - messageTextTextView.measuredWidth,
                currentTop,
                r - l - paddingRight,
                currentTop + messageTextTextView.measuredHeight
            )

            currentTop += messageTextTextView.measuredHeightWithMargins
            reactionFlexboxLayout.layout(
                r - l - paddingRight - reactionFlexboxLayout.measuredWidth,
                currentTop,
                r - l - paddingRight,
                currentTop + reactionFlexboxLayout.measuredHeight
            )

            messageBounds.set(
                messageTextTextView.left.toFloat(),
                messageTextTextView.top.toFloat(),
                messageTextTextView.right.toFloat(),
                messageTextTextView.bottom.toFloat()
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

            messageTextTextView.layout(
                currentLeft,
                currentTop,
                currentLeft + messageTextTextView.measuredWidth,
                currentTop + messageTextTextView.measuredHeight
            )
            currentTop += messageTextTextView.measuredHeightWithMargins

            reactionFlexboxLayout.layout(
                currentLeft,
                currentTop,
                currentLeft + reactionFlexboxLayout.measuredWidth,
                currentTop + reactionFlexboxLayout.measuredHeight
            )

            messageBounds.set(
                (avatarImageView.right + avatarImageView.marginEnd).toFloat(),
                senderNameTextView.top.toFloat(),
                max(senderNameTextView.right, messageTextTextView.right).toFloat(),
                messageTextTextView.bottom.toFloat()
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