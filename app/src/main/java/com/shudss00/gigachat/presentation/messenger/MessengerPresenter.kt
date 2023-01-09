package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.data.source.remote.common.Emoji
import com.shudss00.gigachat.domain.messages.AddReactionUseCase
import com.shudss00.gigachat.domain.messages.DeleteReactionUseCase
import com.shudss00.gigachat.domain.messages.GetMessagesUseCase
import com.shudss00.gigachat.domain.messages.SendStreamMessageUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MessengerPresenter @Inject constructor(
    internal val getMessagesUseCase: GetMessagesUseCase,
    internal val sendStreamMessageUseCase: SendStreamMessageUseCase,
    internal val addReactionUseCase: AddReactionUseCase,
    internal val deleteReactionUseCase: DeleteReactionUseCase
) : RxPresenter<MessengerView>() {

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

    fun onSwipeToRefreshTriggered() {
        getMessages(initialLoading = false)
    }

    fun sendMessage(content: String) {
        sendMessage(streamTitle, topicTitle, content)
    }

    fun addReactionToMessage(messageId: Long, emoji: Emoji) {
        addReactionUseCase(messageId, emoji)
            .async()
            .subscribeBy(
                onComplete = {
                    getMessages(initialLoading = false)
                },
                onError = {
                    Timber.e(it)
                    view?.showErrorToast()
                }
            )
            .disposeOnFinish()
    }

    fun deleteReactionFromMessage(messageId: Long, emoji: Emoji) {
        deleteReactionUseCase(messageId, emoji)
            .async()
            .subscribeBy(
                onComplete = {
                    getMessages(initialLoading = false)
                },
                onError = {
                    Timber.e(it)
                    view?.showErrorToast()
                }
            )
            .disposeOnFinish()
    }

    private fun getMessages(initialLoading: Boolean) {
        getMessagesUseCase(streamTitle, topicTitle)
            .async()
            .doOnSubscribe {
                if (initialLoading) {
                    view?.showFullscreenLoading()
                } else {
                    view?.showPagingLoading()
                }
            }
            .doFinally {
                view?.hideSwipeRefresh()
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
                        view?.showErrorToast()
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
                    Timber.e("begin of message")
                    Timber.e(it)
                    Timber.e("end of message")
                    view?.showErrorToast()
                }
            )
            .disposeOnFinish()
    }
}

