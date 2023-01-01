package com.shudss00.gigachat.data.model.response

import com.shudss00.gigachat.data.model.PresenceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserPresenceResponse(
    @SerialName("presence")
    val presence: Map<String, PresenceDto>
)