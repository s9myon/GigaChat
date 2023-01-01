package com.shudss00.gigachat.domain.model

data class ReactionItem(
    val type: String,
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