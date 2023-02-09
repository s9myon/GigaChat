package com.shudss00.gigachat.domain.model

import com.shudss00.gigachat.domain.common.Emoji

data class Reaction(
    val type: Emoji,
    val reactionNumber: Int,
    val isSelected: Boolean
)