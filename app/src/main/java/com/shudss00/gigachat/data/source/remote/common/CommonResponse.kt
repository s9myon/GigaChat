package com.shudss00.gigachat.data.source.remote.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CommonResponse(
    @SerialName("code")
    override val code: String? = null,
    @SerialName("msg")
    override val msg: String,
    @SerialName("result")
    override val result: String
): ApiResponse