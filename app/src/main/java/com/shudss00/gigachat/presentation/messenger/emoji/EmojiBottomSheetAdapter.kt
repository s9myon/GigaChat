package com.shudss00.gigachat.presentation.messenger.emoji

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.data.source.remote.common.Emoji

class EmojiBottomSheetAdapter(private val listener: OnEmojiClickListener)
    : RecyclerView.Adapter<EmojiBottomSheetAdapter.ViewHolder>() {

    private val emojiList = Emoji.values()

    interface OnEmojiClickListener {
        fun onEmojiClick(emoji: Emoji)
    }

    override fun getItemCount(): Int {
        return emojiList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(emojiList[position])
    }

    class ViewHolder(
        private val view: TextView,
        private val listener: OnEmojiClickListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Emoji) {
            view.text = item.unicode
            view.textSize = 40f
            view.setOnClickListener {
                listener.onEmojiClick(item)
            }
        }
    }
}