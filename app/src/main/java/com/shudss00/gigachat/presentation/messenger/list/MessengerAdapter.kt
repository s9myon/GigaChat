package com.shudss00.gigachat.presentation.messenger.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.shudss00.gigachat.R
import com.shudss00.gigachat.databinding.ViewMessageBinding
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.presentation.widget.EmojiView
import com.shudss00.gigachat.presentation.widget.MessageView

class MessengerAdapter : RecyclerView.Adapter<MessengerAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, ItemCallback())
    var messages: List<MessageItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MessageView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size


    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(message: MessageItem) {
            val binding = ViewMessageBinding.bind(view)
            with(binding) {
                imageViewAvatar.load(message.avatar) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_person_foreground)
                    fallback(R.drawable.ic_person_foreground)
                }
                textViewSenderName.text = message.username
                textViewMessageText.text = message.text

                flexboxLayoutEmojiContainer.removeAllViews()
                message.reactions.forEach { reaction ->
                    val emojiView = EmojiView(flexboxLayoutEmojiContainer.context)
                    emojiView.setText(reaction.type, reaction.reactionNumber)
                    emojiView.isSelected = reaction.isSelected
                    flexboxLayoutEmojiContainer.addView(emojiView)
                }
            }
        }
    }

    private class ItemCallback : DiffUtil.ItemCallback<MessageItem>() {

        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem == newItem
        }
    }
}