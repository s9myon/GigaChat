package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.domain.model.MessageItem
import io.reactivex.Single

interface MessageRepository {

    fun getMessages(streamTitle: String, topicTitle: String): Single<List<MessageItem>>
}