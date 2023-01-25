package com.shudss00.gigachat.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    @SerialName("name")
    val title: String
)