package com.shudss00.gigachat.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("id")
    val id: Long,
    @SerialName("sender_full_name")
    val username: String,
    @SerialName("sender_id")
    val senderId: Long,
    @SerialName("avatar_url")
    val avatar: String? = null,
    @SerialName("content")
    val text: String,
    @SerialName("reactions")
    val reactions: List<ReactionDto>,
    @SerialName("timestamp")
    val timestamp: Long
)