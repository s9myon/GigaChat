package com.shudss00.gigachat.domain.model

data class Message(
    val id: Long,
    val username: String,
    val avatar: String?,
    val text: String,
    val reactions: List<Reaction>,
    val isOwnMessage: Boolean,
    val timestamp: Long
)