package com.shudss00.gigachat.domain.model

data class UserItem(
    val id: Long,
    val name: String,
    val email: String,
    val avatar: String,
    val onlineStatus: String
)