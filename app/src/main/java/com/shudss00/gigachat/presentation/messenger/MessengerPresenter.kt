package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.domain.messages.GetMessagesUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MessengerPresenter @Inject constructor(
    internal val getMessagesUseCase: GetMessagesUseCase
) : RxPresenter<MessengerView>() {

    fun getMessages(streamTitle: String, topicTitle: String) {
        getMessagesUseCase(streamTitle, topicTitle)
            .async()
            .doOnSubscribe {
                view?.showFullscreenLoading()
            }
            .subscribeBy(
                onSuccess = { list ->
                    view?.showMessageList(list)
                },
                onError = {
                    Timber.e(it)
                    view?.showFullscreenError()
                }
            )
            .disposeOnFinish()
    }

}

