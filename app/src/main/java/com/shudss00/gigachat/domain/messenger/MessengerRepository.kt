package com.shudss00.gigachat.domain.messenger

import com.shudss00.gigachat.domain.model.MessageItem
import io.reactivex.Single

interface MessengerRepository {

    fun getMessages(streamTitle: String, topicTitle: String): Single<List<MessageItem>>
}