package com.shudss00.gigachat.data.model.response

import com.shudss00.gigachat.data.model.MessageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMessagesResponse(
    @SerialName("messages")
    val messages: List<MessageDto>
)