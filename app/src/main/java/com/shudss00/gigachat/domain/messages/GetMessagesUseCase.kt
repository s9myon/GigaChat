package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.domain.model.MessageItem
import io.reactivex.Single
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(streamTitle: String, topicTitle: String): Single<List<MessageItem>> {
        return messageRepository.getMessages(streamTitle, topicTitle)
    }
}