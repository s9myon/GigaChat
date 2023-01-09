package com.shudss00.gigachat.domain.messages

import io.reactivex.Completable
import javax.inject.Inject

class SendPrivateMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(userId: Long, content: String): Completable {
        return messageRepository.sendPrivateMessage(userId, content)
    }
}