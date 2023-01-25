package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.R
import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.domain.messages.*
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import com.shudss00.gigachat.presentation.messenger.viewobject.DateItem
import com.shudss00.gigachat.presentation.messenger.viewobject.MessageItem
import com.shudss00.gigachat.presentation.messenger.viewobject.MessengerItem
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

class MessengerPresenter @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendStreamMessageUseCase: SendStreamMessageUseCase,
    private val setReactionToMessageUseCase: SetReactionToMessageUseCase
) : RxPresenter<MessengerView>() {

    private var dataParser = SimpleDateFormat("dd MMM")
    private var streamTitle: String = ""
    private var topicTitle: String = ""

    fun setTitles(streamTitle: String, topicTitle: String) {
        this.streamTitle = streamTitle
        this.topicTitle = topicTitle
    }

    fun onCreate() {
        getMessages(initialLoading = true)
    }

    fun onTryAgainClicked() {
        getMessages(initialLoading = true)
    }

    fun sendMessage(content: String) {
        sendMessage(streamTitle, topicTitle, content)
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
        getMessagesUseCase(streamTitle, topicTitle)
            .map { messageList ->
                val messengerItems = mutableListOf<MessengerItem>()
                var currentDay = ""
                messageList.forEach { message ->
                    val messageSendingTime = dataParser.format(message.timestamp * 1000)
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

    private fun sendMessage(streamTitle: String, topicTitle: String, content: String) {
        sendStreamMessageUseCase(streamTitle, topicTitle, content)
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
}

