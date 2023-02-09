package com.shudss00.gigachat.data.messages

import com.shudss00.gigachat.domain.common.Emoji
import com.shudss00.gigachat.data.source.remote.dto.MessageDto
import com.shudss00.gigachat.domain.model.Message
import com.shudss00.gigachat.domain.model.Reaction
import javax.inject.Inject

class MessageMapper @Inject constructor() {
    fun map(from: MessageDto, ownUserId: Long): Message {
        return Message(
            id = from.id,
            username = from.username,
            avatar = from.avatar,
            text = from.text,
            reactions = from.reactions
                .groupBy { it.emojiName }
                .map { uniqueReaction ->
                    Reaction(
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