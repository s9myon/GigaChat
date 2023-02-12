package com.shudss00.gigachat.presentation.userlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.databinding.ItemUserBinding
import com.shudss00.gigachat.domain.model.User

class UserListAdapter(
    private val listener: UserClickListener
): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    interface UserClickListener {
        fun onUserClick(userId: Long, username: String)
    }

    private val differ = AsyncListDiffer(this, ItemCallback())
    var userItems: List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(userItems[position])
    }

    override fun getItemCount(): Int {
        return userItems.size
    }

    class ViewHolder(
        private val binding: ItemUserBinding,
        private val listener: UserClickListener
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            with(binding) {
                avatarViewUserAvatar.setAvatar(item.avatar)
                avatarViewUserAvatar.setOnlineStatus(item.status)
                textViewUsername.text = item.name
                textViewEmail.text = item.email
                binding.root.setOnClickListener {
                    listener.onUserClick(item.id, item.name)
                }
            }
        }
    }
}

private class ItemCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}