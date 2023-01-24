package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.imageview.ShapeableImageView
import com.shudss00.gigachat.R

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val imageViewAvatar: ShapeableImageView
    private val imageViewOnlineStatus: ShapeableImageView
    init {
        LayoutInflater.from(context).inflate(R.layout.view_avatar, this, true)
        imageViewAvatar = getChildAt(0) as ShapeableImageView
        imageViewOnlineStatus = getChildAt(1) as ShapeableImageView
        context.withStyledAttributes(attrs, R.styleable.AvatarView, defStyleAttr) {
            imageViewAvatar.setImageResource(
                getResourceId(
                    R.styleable.AvatarView_avatarSrc,
                    R.drawable.ic_person_foreground)
            )
            setOnlineStatus(
                OnlineStatus.from(
                    getInt(R.styleable.AvatarView_onlineStatus, 2)
                )
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChild(imageViewAvatar, widthMeasureSpec, heightMeasureSpec)
        measureChild(imageViewOnlineStatus, widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(
            imageViewAvatar.measuredWidth,
            imageViewAvatar.measuredHeight
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        imageViewAvatar.layout(
            paddingLeft,
            paddingTop,
            paddingLeft + imageViewAvatar.measuredWidth,
            paddingTop + imageViewAvatar.measuredHeight
        )
        imageViewOnlineStatus.layout(
            r - l - paddingRight - imageViewOnlineStatus.measuredWidth,
            b - t - paddingBottom - imageViewOnlineStatus.measuredHeight,
            r - l - paddingRight,
            b - t - paddingBottom
        )
    }

    fun setAvatar(avatar: String?) {
        imageViewAvatar.load(avatar) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_person_foreground)
            fallback(R.drawable.ic_person_foreground)
        }
    }

    fun setOnlineStatus(status: OnlineStatus) {
        when (status.code) {
            0 -> imageViewOnlineStatus.setImageResource(R.drawable.ic_active)
            1 -> imageViewOnlineStatus.setImageResource(R.drawable.ic_idle)
            2 -> imageViewOnlineStatus.setImageResource(R.drawable.ic_offline)
        }
    }

    enum class OnlineStatus(val code: Int) {
        ACTIVE(0),
        IDLE(1),
        OFFLINE(2);

        companion object {
            fun from(code: Int): OnlineStatus {
                return OnlineStatus.values().find { it.code == code }
                    ?: throw IllegalArgumentException("The number is not included in the range from 0 to 2")
            }
        }
    }
}