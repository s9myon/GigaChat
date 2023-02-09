package com.shudss00.gigachat.data.source.remote.streams

import com.shudss00.gigachat.data.source.remote.common.ApiResponse
import com.shudss00.gigachat.data.source.remote.dto.StreamDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllStreamsResponse(
    @SerialName("code")
    override val code: String? = null,
    @SerialName("msg")
    override val msg: String,
    @SerialName("result")
    override val result: String,
    @SerialName("streams")
    val streams: List<StreamDto>
) : ApiResponse