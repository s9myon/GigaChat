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
import com.shudss00.gigachat.domain.common.OnlineStatus

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
        invalidate()
    }

    fun setOnlineStatus(status: OnlineStatus) {
        when (status) {
            OnlineStatus.ACTIVE -> imageViewOnlineStatus.setImageResource(R.drawable.ic_active)
            OnlineStatus.IDLE -> imageViewOnlineStatus.setImageResource(R.drawable.ic_idle)
            OnlineStatus.OFFLINE -> imageViewOnlineStatus.setImageResource(R.drawable.ic_offline)
            OnlineStatus.INDEFINITE -> imageViewOnlineStatus.setImageResource(android.R.color.transparent)
        }
        invalidate()
    }
}