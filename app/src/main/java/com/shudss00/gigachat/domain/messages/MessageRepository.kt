package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.domain.common.Emoji
import com.shudss00.gigachat.domain.model.Message
import io.reactivex.Completable
import io.reactivex.Single

interface MessageRepository {
    fun getStreamMessages(streamTitle: String, topicTitle: String): Single<List<Message>>

    fun getPrivateMessages(userId: Long): Single<List<Message>>

    fun sendPrivateMessage(userId: Long, content: String): Completable

    fun sendStreamMessage(streamTitle: String, topicTitle: String, content: String): Completable

    fun setReactionToMessage(messageId: Long, emoji: Emoji): Completable
}