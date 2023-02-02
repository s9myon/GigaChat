package com.shudss00.gigachat.domain.model

import com.shudss00.gigachat.domain.utils.OnlineStatus

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val avatar: String,
    val onlineStatus: OnlineStatus
)