package com.shudss00.gigachat.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PresenceDto(
    @SerialName("status")
    val status: String
)