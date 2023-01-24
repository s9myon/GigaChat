package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.extensions.hide
import com.shudss00.gigachat.presentation.extensions.show

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val errorImageView: ImageView
    private val errorTextView: TextView
    private val errorButton: Button

    init {
        LayoutInflater.from(context).inflate(R.layout.view_state_error, this, true)
        errorImageView = findViewById(R.id.imageView_errorImage)
        errorTextView = findViewById(R.id.textView_errorText)
        errorButton = findViewById(R.id.button_errorButton)
        gravity = Gravity.CENTER
        orientation = VERTICAL
        context.withStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr) {
            errorImageView.setImageResource(
                getResourceId(
                    R.styleable.ErrorView_errorImageSrc,
                    R.drawable.placeholder_not_found
                )
            )
            errorTextView.setText(
                getResourceId(
                    R.styleable.ErrorView_errorText,
                    R.string.error_failed_load_data
                )
            )
            errorButton.setText(
                getResourceId(
                    R.styleable.ErrorView_buttonText,
                    R.string.error_try_again
                )
            )
        }
    }

    fun setErrorText(@StringRes text: Int) {
        errorTextView.setText(text)
    }

    fun setErrorImage(@DrawableRes image: Int) {
        errorImageView.show()
        errorImageView.setImageResource(image)
    }

    fun hideErrorImage() {
        errorImageView.hide()
    }

    fun setErrorButton(@StringRes text: Int, onClickListener: () -> Unit) {
        errorButton.show()
        errorButton.setText(text)
        errorButton.setOnClickListener { onClickListener.invoke() }
    }

    fun hideErrorButton() {
        errorButton.hide()
    }
}