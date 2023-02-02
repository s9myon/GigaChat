package com.shudss00.gigachat.presentation.streamlist

import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.streams.GetAllStreamsUseCase
import com.shudss00.gigachat.domain.streams.GetSubscribedStreamsUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamItem
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem
import com.shudss00.gigachat.presentation.streamlist.listitems.TopicItem
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class StreamListPresenter @Inject constructor(
    private val getAllStreamsUseCase: GetAllStreamsUseCase,
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase
) : RxPresenter<StreamListView>() {

    private val expandedStreamItemTitles = linkedSetOf<String>()
    private val messagesNumberTemporaryStub = 666

    fun onCreate() {
        getAllStreams(null)
    }

    fun getAllStreams(expandableStreamItemTitle: String?) {
        expandStreamItem(expandableStreamItemTitle)
        getAllStreamsUseCase()
            .map { list ->
                val streamItems = mutableListOf<StreamListItem>()
                list.forEach { stream ->
                    val isStreamItemExpanded = expandedStreamItemTitles.contains(stream.title)
                    streamItems.add(
                        StreamItem(
                            id = stream.id,
                            title = stream.title,
                            isExpanded = isStreamItemExpanded
                        )
                    )
                    if (isStreamItemExpanded) {
                        stream.topics.forEach { topic ->
                            streamItems.add(
                                TopicItem(
                                    topicTitle = topic.title,
                                    streamTitle = stream.title,
                                    messageCount = messagesNumberTemporaryStub
                                )
                            )
                        }
                    }
                }
                streamItems
            }
            .async()
            .subscribeBy(
                onSuccess = { list ->
                    view?.showStreamList(list)
                },
                onError = {
                    Timber.e(it)
                    view?.showErrorToast(R.string.error_failed_update_data)
                }
            )
            .disposeOnFinish()
    }

    private fun expandStreamItem(expandStreamTitle: String?) {
        if (expandStreamTitle != null) {
            if (!expandedStreamItemTitles.remove(expandStreamTitle)) {
                expandedStreamItemTitles.add(expandStreamTitle)
            }
        }
    }
}