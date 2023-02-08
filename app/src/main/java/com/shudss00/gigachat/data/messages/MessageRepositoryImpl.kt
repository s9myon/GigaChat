package com.shudss00.gigachat.data.messages

import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.data.source.remote.messages.MessageApi
import com.shudss00.gigachat.data.source.remote.messages.NarrowBuilder
import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.domain.messages.MessageRepository
import com.shudss00.gigachat.domain.model.Message
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageApi: MessageApi,
    private val userApi: UserApi,
    private val messageMapper: MessageMapper
) : MessageRepository {

    override fun getStreamMessages(streamTitle: String, topicTitle: String): Single<List<Message>> {
        return Single.zip(
            messageApi.getMessages(
                narrows = NarrowBuilder()
                    .stream(streamTitle)
                    .topic(topicTitle)
                    .build()
            ).map { it.messages },
            userApi.getOwnUser()
        ) { messages, ownUser ->
            messages.map { message ->
                messageMapper.map(message, ownUser.id)
            }
        }
    }

    override fun getPrivateMessages(userId: Long): Single<List<Message>> {
        return Single.zip(
            messageApi.getMessages(
                narrows = NarrowBuilder()
                    .privateMessagesWith(userId)
                    .build()
            ).map { it.messages },
            userApi.getOwnUser()
        ) { messages, ownUser ->
            messages.map { message ->
                messageMapper.map(message, ownUser.id)
            }
        }
    }

    override fun sendPrivateMessage(userId: Long, content: String): Completable {
        return messageApi.sendMessage(
            type = "private",
            to = Json.encodeToString(listOf(userId)),
            content = content
        )
    }

    override fun sendStreamMessage(
        streamTitle: String,
        topicTitle: String,
        content: String
    ): Completable {
        return messageApi.sendMessage(
            type = "stream",
            to = streamTitle,
            topic = topicTitle,
            content = content
        )
    }

    override fun setReactionToMessage(messageId: Long, emoji: Emoji): Completable {
        return Single.zip(
            messageApi.getSingleMessage(messageId).map { it.message },
            userApi.getOwnUser()
        ) { message, ownUser ->
            message.reactions.any { it.emojiName == emoji.emojiName && it.userId == ownUser.id }
        }.flatMapCompletable { isReactionAlreadySet ->
            if (isReactionAlreadySet) {
                messageApi.deleteReaction(messageId, emoji.emojiName)
            } else {
                messageApi.addReaction(messageId, emoji.emojiName)
            }
        }
    }
}