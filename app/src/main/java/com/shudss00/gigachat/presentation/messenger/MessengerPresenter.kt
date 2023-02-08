package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.R
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.domain.messages.*
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import com.shudss00.gigachat.presentation.extensions.formatTimestamp
import com.shudss00.gigachat.presentation.messenger.listitems.DateItem
import com.shudss00.gigachat.presentation.messenger.listitems.MessageItem
import com.shudss00.gigachat.presentation.messenger.listitems.MessengerItem
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MessengerPresenter @Inject constructor(
    private val getStreamMessagesUseCase: GetStreamMessagesUseCase,
    private val getPrivateMessagesUseCase: GetPrivateMessagesUseCase,
    private val sendStreamMessageUseCase: SendStreamMessageUseCase,
    private val sendPrivateMessageUseCase: SendPrivateMessageUseCase,
    private val setReactionToMessageUseCase: SetReactionToMessageUseCase
) : RxPresenter<MessengerView>() {


    private var streamTitle: String = ""
    private var topicTitle: String = ""
    private var companionUserId: Long = -1

    fun setTitles(streamTitle: String, topicTitle: String, companionUserId: Long) {
        if (streamTitle != "" && topicTitle != "") {
            this.streamTitle = streamTitle
            this.topicTitle = topicTitle
        }
        if (companionUserId != -1L) {
            this.companionUserId = companionUserId
        }
    }

    fun onCreate() {
        getMessages(initialLoading = true)
    }

    fun onTryAgainClicked() {
        getMessages(initialLoading = true)
    }

    fun sendMessage(content: String) {
        when {
            streamTitle.isNotBlank() && topicTitle.isNotBlank() ->
                sendStreamMessageUseCase(streamTitle, topicTitle, content)
            companionUserId != -1L ->
                sendPrivateMessageUseCase(companionUserId, content)
            else -> Completable.error(Exception("The name of the stream and the name of the topic" +
                    " or the user ID have not been transferred to the MessengerPresenter"))
        }
            .async()
            .subscribeBy(
                onComplete = {
                    getMessages(initialLoading = false)
                },
                onError = {
                    Timber.e(it)
                    view?.showErrorToast(R.string.error_failed_update_data)
                }
            )
            .disposeOnFinish()
    }

    fun setReactionToMessage(messageId: Long, emoji: Emoji) {
        setReactionToMessageUseCase(messageId, emoji)
            .async()
            .subscribeBy(
                onComplete = {
                    getMessages(initialLoading = false)
                },
                onError = {
                    Timber.e(it)
                    view?.showErrorToast(R.string.error_failed_send_data)
                }
            )
            .disposeOnFinish()
    }

    private fun getMessages(initialLoading: Boolean) {
        when {
            streamTitle.isNotBlank() && topicTitle.isNotBlank() ->
                getStreamMessagesUseCase(streamTitle, topicTitle)
            companionUserId != -1L ->
                getPrivateMessagesUseCase(companionUserId)
            else -> Single.error(Exception("The name of the stream and the name of the topic" +
                    " or the user ID have not been transferred to the MessengerPresenter"))
        }
            .map { messageList ->
                val messengerItems = mutableListOf<MessengerItem>()
                var currentDay = ""
                messageList.forEach { message ->
                    val messageSendingTime = formatTimestamp(message.timestamp)
                    if (currentDay != messageSendingTime) {
                        messengerItems.add(DateItem(date = messageSendingTime))
                        currentDay = messageSendingTime
                    }
                    messengerItems.add(
                        MessageItem(
                            id = message.id,
                            username = message.username,
                            avatar = message.avatar,
                            text = message.text,
                            reactions = message.reactions,
                            isOwnMessage = message.isOwnMessage
                        )
                    )
                }
                messengerItems
            }
            .async()
            .doOnSubscribe {
                if (initialLoading) {
                    view?.showFullscreenLoading()
                } else {
                    view?.showPagingLoading()
                }
            }
            .subscribeBy(
                onSuccess = { list ->
                    view?.showMessageList(list)
                },
                onError = {
                    Timber.e(it)
                    if (initialLoading) {
                        view?.showFullscreenError()
                    } else {
                        view?.showErrorToast(R.string.error_failed_update_data)
                    }
                }
            )
            .disposeOnFinish()
    }
}
