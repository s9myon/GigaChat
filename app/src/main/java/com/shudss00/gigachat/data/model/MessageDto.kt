package com.shudss00.gigachat.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("id")
    val id: Long,
    @SerialName("sender_full_name")
    val username: String,
    @SerialName("avatar_url")
    val avatar: String,
    @SerialName("content")
    val text: String,
    @SerialName("reactions")
    val reactions: List<ReactionDto>
)