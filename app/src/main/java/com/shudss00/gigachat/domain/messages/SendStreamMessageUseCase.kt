package com.shudss00.gigachat.domain.messages

import io.reactivex.Completable
import javax.inject.Inject

class SendStreamMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(streamTitle: String, topicTitle: String, content: String): Completable {
        return messageRepository.sendStreamMessage(streamTitle, topicTitle, content)
    }
}