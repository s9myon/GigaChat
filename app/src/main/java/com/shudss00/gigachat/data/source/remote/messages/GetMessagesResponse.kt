package com.shudss00.gigachat.data.source.remote.messages

import com.shudss00.gigachat.data.source.remote.model.MessageDto
import com.shudss00.gigachat.data.source.remote.common.ApiResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMessagesResponse(
    @SerialName("code")
    override val code: String? = null,
    @SerialName("msg")
    override val msg: String,
    @SerialName("result")
    override val result: String,
    @SerialName("messages")
    val messages: List<MessageDto>
) : ApiResponse