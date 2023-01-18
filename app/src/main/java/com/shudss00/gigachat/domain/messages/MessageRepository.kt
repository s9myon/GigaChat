package com.shudss00.gigachat.domain.messages

import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.domain.model.MessageItem
import io.reactivex.Completable
import io.reactivex.Single

interface MessageRepository {
    fun getMessages(streamTitle: String, topicTitle: String): Single<List<MessageItem>>

    fun sendPrivateMessage(userId: Long, content: String): Completable

    fun sendStreamMessage(streamTitle: String, topicTitle: String, content: String): Completable

    fun setReactionToMessage(messageId: Long, emoji: Emoji): Completable
}