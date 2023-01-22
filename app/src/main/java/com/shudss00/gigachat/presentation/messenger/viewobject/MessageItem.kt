package com.shudss00.gigachat.presentation.messenger.viewobject

import com.shudss00.gigachat.domain.model.Reaction

data class MessageItem(
    val id: Long,
    val username: String,
    val avatar: String?,
    val text: String,
    val reactions: List<Reaction>,
    val isOwnMessage: Boolean
) : MessengerItem