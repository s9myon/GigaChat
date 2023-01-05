package com.shudss00.gigachat.data.messenger

import com.shudss00.gigachat.data.source.remote.messenger.MessengerApi
import com.shudss00.gigachat.data.source.remote.user.UserApi
import com.shudss00.gigachat.domain.messenger.MessengerRepository
import com.shudss00.gigachat.domain.model.MessageItem
import com.shudss00.gigachat.domain.model.ReactionItem
import io.reactivex.Single
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MessengerRepositoryImpl @Inject constructor(
    private val messengerApi: MessengerApi,
    private val userApi: UserApi
) : MessengerRepository {

    @Serializable
    class Narrow(
        @SerialName("negated")
        val negated: Boolean = false,
        @SerialName("operator")
        val operator: String,
        @SerialName("operand")
        val operand: String
    )

    override fun getMessages(
        streamTitle: String,
        topicTitle: String
    ): Single<List<MessageItem>> {
        return messengerApi.getMessages(
            narrows = Json.encodeToString(
                listOf(
                    Narrow(
                        operator = "stream",
                        operand = streamTitle
                    ),
                    Narrow(
                        operator = "topic",
                        operand = topicTitle
                    )
                )
            )
        ).flatMap { response ->
            userApi.getOwnUser().map { ownUser ->
                response.messages.map { message ->
                    MessageItem(
                        id = message.id,
                        username = message.username,
                        avatar = message.avatar,
                        text = message.text,
                        reactions = message.reactions
                            .distinctBy { it.emojiName }
                            .map { reaction ->
                                val oneTypeReactions = message.reactions.filter {
                                    it.emojiName == reaction.emojiName
                                }
                                ReactionItem(
                                    type = reaction.emojiName,
                                    reactionNumber = oneTypeReactions.count(),
                                    isSelected = oneTypeReactions.any { it.userId == ownUser.id }
                                )
                            }
                    )
                }
            }
        }
    }
}