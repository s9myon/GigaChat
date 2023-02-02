package com.shudss00.gigachat.presentation.messenger.listitems

import com.shudss00.gigachat.domain.model.Reaction

data class MessageItem(
    val id: Long,
    val username: String,
    val avatar: String?,
    val text: String,
    val reactions: List<Reaction>,
    val isOwnMessage: Boolean
) : MessengerItem