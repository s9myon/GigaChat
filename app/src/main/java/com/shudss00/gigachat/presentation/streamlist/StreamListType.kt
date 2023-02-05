package com.shudss00.gigachat.presentation.streamlist

import androidx.annotation.StringRes
import com.shudss00.gigachat.R

enum class StreamListType(
    val position: Int,
    @StringRes val stringRes: Int
) {
    SUBSCRIBED(0, R.string.tabItem_subscribed),
    ALL_STREAMS(1, R.string.tabItem_allStreams)
}