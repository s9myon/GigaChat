package com.shudss00.gigachat.data.source.remote.users

import com.shudss00.gigachat.data.source.remote.model.PresenceDto
import com.shudss00.gigachat.data.source.remote.common.ApiResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllUsersPresenceResponse(
    @SerialName("code")
    override val code: String? = null,
    @SerialName("msg")
    override val msg: String,
    @SerialName("result")
    override val result: String,
    @SerialName("presences")
    val presences: Map<String, Map<String, PresenceDto>>
) : ApiResponse