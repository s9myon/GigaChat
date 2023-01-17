package com.shudss00.gigachat.data.messages

import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.data.source.remote.messages.MessageApi
import com.shudss00.gigachat.data.source.remote.messages.NarrowBuilder
import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.domain.messages.MessageRepository
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.domain.model.ReactionItem
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageApi: MessageApi,
    private val userApi: UserApi
) : MessageRepository {

    override fun getMessages(streamTitle: String, topicTitle: String): Single<List<MessageItem>> {
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
                MessageItem(
                    id = message.id,
                    username = message.username,
                    avatar = message.avatar,
                    text = message.text,
                    reactions = message.reactions
                        .groupBy { it.emojiName }
                        .map { uniqueReaction ->
                            ReactionItem(
                                type = Emoji.from(uniqueReaction.key),
                                reactionNumber = uniqueReaction.value.size,
                                isSelected = uniqueReaction.value.any { it.userId == ownUser.id }
                            )
                        }
                )
            }
        }
    }

    override fun sendPrivateMessage(userId: Long, content: String): Completable {
        return messageApi.sendMessage(
            type = "private",
            to = userId.toString(),
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

    override fun addReaction(messageId: Long, emoji: Emoji): Completable {
        return messageApi.addReaction(
            messageId = messageId,
            emojiName = emoji.emojiName
        )
    }

    override fun deleteReaction(messageId: Long, emoji: Emoji): Completable {
        return messageApi.deleteReaction(
            messageId = messageId,
            emojiName = emoji.emojiName
        )
    }
}