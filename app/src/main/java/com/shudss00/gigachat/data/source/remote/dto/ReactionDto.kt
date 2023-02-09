package com.shudss00.gigachat.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReactionDto(
    @SerialName("emoji_name")
    val emojiName: String,
    @SerialName("user_id")
    val userId: Long
)