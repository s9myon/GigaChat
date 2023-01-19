package com.shudss00.gigachat.presentation.messenger

import androidx.annotation.StringRes
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.base.MvpView

interface MessengerView : MvpView {
    fun onChangeMessageState(item: MessageItem)
    fun showMessageList(items: List<MessageItem>)
    fun showErrorToast(@StringRes text: Int)
    fun showPagingLoading()
    fun showFullscreenError()
    fun showFullscreenLoading()
}