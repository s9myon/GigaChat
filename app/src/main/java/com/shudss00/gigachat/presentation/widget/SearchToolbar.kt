package com.shudss00.gigachat.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.content.withStyledAttributes
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.extensions.hide
import com.shudss00.gigachat.presentation.extensions.show

class SearchToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val editTextSearchBox: EditText
    private val buttonSearch: ImageButton
    private val elevationView: View
    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_toolbar, this, true)
        editTextSearchBox = getChildAt(0) as EditText
        buttonSearch = getChildAt(1) as ImageButton
        elevationView = getChildAt(2) as View
        context.withStyledAttributes(attrs, R.styleable.SearchToolbar, defStyleAttr) {
            editTextSearchBox.setHint(
                getResourceId(
                    R.styleable.SearchToolbar_titleHint,
                    R.string.editText_searchStreamHint
                )
            )
            setElevationVisibility(
                getBoolean(
                    R.styleable.SearchToolbar_isElevationVisible,
                    true
                )
            )
        }
    }

    fun setOnSearchButtonClickListener(onClickListener: () -> Unit) {
        buttonSearch.setOnClickListener {
            onClickListener.invoke()
        }
    }

    fun setElevationVisibility(shouldBeVisible: Boolean) {
        if (shouldBeVisible) {
            elevationView.show()
        } else {
            elevationView.hide()
        }
    }
}