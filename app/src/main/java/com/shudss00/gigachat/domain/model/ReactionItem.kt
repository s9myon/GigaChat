package com.shudss00.gigachat.domain.model

import com.shudss00.gigachat.data.source.remote.common.Emoji

data class ReactionItem(
    val type: Emoji,
    val reactionNumber: Int,
    val isSelected: Boolean
) {

    fun select(): ReactionItem {
        return if (isSelected) {
            copy(
                reactionNumber = reactionNumber - 1,
                isSelected = false
            )
        } else {
            copy(
                reactionNumber = reactionNumber + 1,
                isSelected = true
            )
        }
    }
}