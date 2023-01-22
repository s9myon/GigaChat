package com.shudss00.gigachat.presentation.messenger.list

import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.presentation.messenger.viewobject.MessageItem
import com.shudss00.gigachat.presentation.widget.MessageView

class MessageViewHolder(private val view: MessageView) : RecyclerView.ViewHolder(view) {
    fun bind(item: MessageItem) {
        view.setMessageItem(item)
    }
}