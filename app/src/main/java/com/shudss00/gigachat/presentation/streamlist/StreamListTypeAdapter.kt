package com.shudss00.gigachat.presentation.streamlist

import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.presentation.streamlist.list.StreamListAdapter
import com.shudss00.gigachat.presentation.streamlist.list.StreamViewHolder
import com.shudss00.gigachat.presentation.streamlist.list.TopicViewHolder
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem

class StreamListTypeAdapter(
    private val streamItemClickListener: StreamViewHolder.StreamItemClickListener,
    private val topicItemClickListener: TopicViewHolder.TopicItemClickListener
) : RecyclerView.Adapter<StreamListTypeAdapter.ViewHolder>() {

    private val streamListTypes = StreamListType.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerView(parent.context).apply {
                adapter = StreamListAdapter(streamItemClickListener, topicItemClickListener)
                layoutManager = LinearLayoutManager(parent.context)
                layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT
                )
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = streamListTypes.size

    class ViewHolder(private val recyclerView: RecyclerView) : RecyclerView.ViewHolder(recyclerView) {

        fun updateList(list: List<StreamListItem>) {
            (recyclerView.adapter as StreamListAdapter).streamListItems = list
        }
    }
}