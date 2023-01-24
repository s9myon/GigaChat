package com.shudss00.gigachat.presentation.messenger.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.messenger.viewobject.DateItem
import com.shudss00.gigachat.presentation.messenger.viewobject.MessageItem
import com.shudss00.gigachat.presentation.messenger.viewobject.MessengerItem
import com.shudss00.gigachat.presentation.widget.MessageView

private const val DATE_ITEM = 0
private const val MESSAGE_ITEM = 1

class MessengerAdapter(
    private val listener: MessageView.MessageClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(this, ItemCallback())
    var messengerItems: List<MessengerItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemViewType(position: Int): Int {
        return when {
            messengerItems[position] is DateItem -> DATE_ITEM
            messengerItems[position] is MessageItem -> MESSAGE_ITEM
            else -> throw Exception("No such viewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            DATE_ITEM -> {
                DateViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_date_separator, parent, false)
                )
            }
            MESSAGE_ITEM -> {
                MessageViewHolder(
                    MessageView(parent.context).apply {
                        messageClickListener = listener
                    }
                )
            }
            else -> throw Exception("No such viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> holder.bind(messengerItems[position] as DateItem)
            is MessageViewHolder -> holder.bind(messengerItems[position] as MessageItem)
            else -> throw Exception("No such viewType")
        }
    }

    override fun getItemCount(): Int = messengerItems.size
}

private class ItemCallback : DiffUtil.ItemCallback<MessengerItem>() {

    override fun areItemsTheSame(oldItem: MessengerItem, newItem: MessengerItem): Boolean {
        return when {
            oldItem is DateItem && newItem is DateItem -> oldItem.date == newItem.date
            oldItem is MessageItem && newItem is MessageItem -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: MessengerItem, newItem: MessengerItem): Boolean {
        return when {
            oldItem is DateItem && newItem is DateItem -> oldItem.date == newItem.date
            oldItem is MessageItem && newItem is MessageItem -> {
                (oldItem as MessageItem) == (newItem as MessageItem)
            }
            else -> false
        }
    }
}