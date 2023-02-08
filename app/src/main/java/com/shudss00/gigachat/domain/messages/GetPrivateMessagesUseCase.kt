package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.domain.model.Message
import io.reactivex.Single
import javax.inject.Inject

class GetPrivateMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(userId: Long): Single<List<Message>> {
        return messageRepository.getPrivateMessages(userId)
    }
}