package com.shudss00.gigachat.data.model.response

import com.shudss00.gigachat.data.model.PresenceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetAllUsersPresenceResponse(
    @SerialName("presences")
    val presences: Map<String, Map<String, PresenceDto>>
)