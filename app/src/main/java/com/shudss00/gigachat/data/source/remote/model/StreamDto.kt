package com.shudss00.gigachat.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamDto(
    @SerialName("stream_id")
    val id: Long,
    @SerialName("name")
    val title: String
)