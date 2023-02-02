package com.shudss00.gigachat.presentation.streamlist.list

import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.databinding.ItemTopicBinding
import com.shudss00.gigachat.presentation.streamlist.listitems.TopicItem

class TopicViewHolder(
    private val binding: ItemTopicBinding,
    private val listener: TopicItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    interface TopicItemClickListener {
        fun onTopicItemClick(streamTitle: String, topicTitle: String)
    }

    fun bind(item: TopicItem) {
        with(binding) {
            textViewTitle.text = item.topicTitle
            textViewMessageCount.text = item.messageCount.toString()
            layoutTopic.setOnClickListener {
                listener.onTopicItemClick(item.streamTitle, item.topicTitle)
            }
        }
    }
}