package com.shudss00.gigachat.domain.model

data class MessageItem(
    val id: Long,
    val username: String,
    val avatar: String?,
    val text: String,
    val reactions: List<ReactionItem>
)