package com.shudss00.gigachat.presentation.messenger.list

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.widget.MessageView

class MessengerAdapter(
    private val listener: MessageView.MessageClickListener
) : RecyclerView.Adapter<MessengerAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, ItemCallback())
    var messages: List<MessageItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MessageView(parent.context).apply {
            messageClickListener = listener
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setMessageItem(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    class ViewHolder(val view: MessageView) : RecyclerView.ViewHolder(view)
}

private class ItemCallback : DiffUtil.ItemCallback<MessageItem>() {

    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }
}