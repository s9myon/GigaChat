package com.shudss00.gigachat.presentation.streamlist

import androidx.annotation.StringRes
import com.shudss00.gigachat.presentation.base.MvpView
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem

interface StreamListView : MvpView {
    fun showStreamList(list: List<StreamListItem>)
    fun showErrorToast(@StringRes text: Int)
}