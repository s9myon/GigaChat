package com.shudss00.gigachat.domain.model

import com.shudss00.gigachat.data.source.remote.common.Emoji

data class Reaction(
    val type: Emoji,
    val reactionNumber: Int,
    val isSelected: Boolean
)