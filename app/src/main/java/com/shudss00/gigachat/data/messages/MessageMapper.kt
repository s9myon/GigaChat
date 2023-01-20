package com.shudss00.gigachat.data.messages

import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.data.source.remote.model.MessageDto
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.domain.model.ReactionItem
import javax.inject.Inject

class MessageMapper @Inject constructor() {
    fun map(from: MessageDto, ownUserId: Long): MessageItem {
        return MessageItem(
            id = from.id,
            username = from.username,
            avatar = from.avatar,
            text = from.text,
            reactions = from.reactions
                .groupBy { it.emojiName }
                .map { uniqueReaction ->
                    ReactionItem(
                        type = Emoji.from(uniqueReaction.key),
                        reactionNumber = uniqueReaction.value.size,
                        isSelected = uniqueReaction.value.any { it.userId == ownUserId }
                    )
                },
            isOwnMessage = from.senderId == ownUserId,
            timestamp = from.timestamp
        )
    }
}