package com.shudss00.gigachat.presentation.streamlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.databinding.ItemStreamBinding
import com.shudss00.gigachat.databinding.ItemTopicBinding
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamItem
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem
import com.shudss00.gigachat.presentation.streamlist.listitems.TopicItem

private const val STREAM_ITEM = 0
private const val TOPIC_ITEM = 1

class StreamListAdapter(
    private val streamItemClickListener: StreamViewHolder.StreamItemClickListener,
    private val topicItemClickListener: TopicViewHolder.TopicItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, ItemCallback())
    var streamListItems: List<StreamListItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemViewType(position: Int): Int {
        return when {
            streamListItems[position] is TopicItem -> TOPIC_ITEM
            streamListItems[position] is StreamItem -> STREAM_ITEM
            else -> throw Exception("No such viewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TOPIC_ITEM ->
                TopicViewHolder(
                    ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    topicItemClickListener
                )
            STREAM_ITEM ->
                StreamViewHolder(
                    ItemStreamBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    streamItemClickListener
                )
            else -> throw Exception("No such viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is TopicViewHolder -> holder.bind(streamListItems[position] as TopicItem)
            is StreamViewHolder -> holder.bind(streamListItems[position] as StreamItem)
            else -> throw Exception("No such viewType")
        }
    }

    override fun getItemCount(): Int = streamListItems.size
}

private class ItemCallback: DiffUtil.ItemCallback<StreamListItem>() {

    override fun areItemsTheSame(oldItem: StreamListItem, newItem: StreamListItem): Boolean {
        return when {
            oldItem is TopicItem && newItem is TopicItem ->
                oldItem.topicTitle == newItem.topicTitle && oldItem.messageCount == newItem.messageCount
            oldItem is StreamItem && newItem is StreamItem -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: StreamListItem, newItem: StreamListItem): Boolean {
        return when {
            oldItem is TopicItem && newItem is TopicItem ->
                (oldItem as TopicItem) == (newItem as TopicItem)
            oldItem is StreamItem && newItem is StreamItem ->
                (oldItem as StreamItem) == (newItem as StreamItem)
            else -> false
        }
    }
}