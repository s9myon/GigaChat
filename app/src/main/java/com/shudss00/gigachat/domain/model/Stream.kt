package com.shudss00.gigachat.domain.model

data class Stream(
    val id: Long,
    val title: String,
    val topics: List<Topic>
)