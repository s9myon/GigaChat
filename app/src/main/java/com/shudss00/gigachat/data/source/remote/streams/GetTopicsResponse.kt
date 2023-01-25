package com.shudss00.gigachat.data.source.remote.streams

import com.shudss00.gigachat.data.source.remote.common.ApiResponse
import com.shudss00.gigachat.data.source.remote.model.TopicDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTopicsResponse(
    @SerialName("code")
    override val code: String? = null,
    @SerialName("msg")
    override val msg: String,
    @SerialName("result")
    override val result: String,
    @SerialName("topics")
    val topics: List<TopicDto>
) : ApiResponse