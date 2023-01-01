package com.shudss00.gigachat.domain.messenger

import com.shudss00.gigachat.domain.model.MessageItem
import io.reactivex.Single
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messengerRepository: MessengerRepository
) {

    operator fun invoke(streamTitle: String, topicTitle: String): Single<List<MessageItem>> {
        return messengerRepository.getMessages(streamTitle, topicTitle)
    }
}