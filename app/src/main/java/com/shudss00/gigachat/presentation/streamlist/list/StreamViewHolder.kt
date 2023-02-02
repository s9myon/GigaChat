package com.shudss00.gigachat.presentation.streamlist.list

import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.R
import com.shudss00.gigachat.databinding.ItemStreamBinding
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamItem

class StreamViewHolder(
    private val binding: ItemStreamBinding,
    private val listener: StreamItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    interface StreamItemClickListener {
        fun onStreamItemClick(streamTitle: String)
    }

    fun bind(item: StreamItem) {
        with (binding) {
            textViewStreamTitle.text = item.title
            if (item.isExpanded) {
                buttonArrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                buttonArrow.setImageResource(R.drawable.ic_arrow_down)
            }
            buttonArrow.setOnClickListener {
                listener.onStreamItemClick(item.title)
            }
        }
    }
}