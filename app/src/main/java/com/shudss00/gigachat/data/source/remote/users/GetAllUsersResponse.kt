package com.shudss00.gigachat.data.source.remote.users

import com.shudss00.gigachat.data.source.remote.common.ApiResponse
import com.shudss00.gigachat.data.source.remote.model.UserDto
import kotlinx.serialization.SerialName

data class GetAllUsersResponse(
    @SerialName("code")
    override val code: String? = null,
    @SerialName("msg")
    override val msg: String,
    @SerialName("result")
    override val result: String,
    @SerialName("members")
    val members: List<UserDto>
) : ApiResponse