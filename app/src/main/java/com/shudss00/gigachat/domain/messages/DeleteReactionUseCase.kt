package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.data.source.remote.common.Emoji
import io.reactivex.Completable
import javax.inject.Inject

class DeleteReactionUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(messageId: Long, emoji: Emoji): Completable {
        return messageRepository.deleteReaction(messageId, emoji)
    }
}