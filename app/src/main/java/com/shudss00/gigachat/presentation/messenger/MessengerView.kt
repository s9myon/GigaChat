package com.shudss00.gigachat.presentation.messenger

import androidx.annotation.StringRes
import com.shudss00.gigachat.domain.model.Message
import com.shudss00.gigachat.presentation.base.MvpView
import com.shudss00.gigachat.presentation.messenger.viewobject.MessengerItem

interface MessengerView : MvpView {
    fun onChangeMessageState(item: Message)
    fun showMessageList(items: List<MessengerItem>)
    fun showErrorToast(@StringRes text: Int)
    fun showPagingLoading()
    fun showFullscreenError()
    fun showFullscreenLoading()
}