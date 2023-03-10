package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.domain.model.Message
import io.reactivex.Single
import javax.inject.Inject

class GetStreamMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(streamTitle: String, topicTitle: String): Single<List<Message>> {
        return messageRepository.getStreamMessages(streamTitle, topicTitle)
    }
}