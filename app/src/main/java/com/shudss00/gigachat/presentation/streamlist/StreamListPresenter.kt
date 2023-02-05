package com.shudss00.gigachat.presentation.streamlist

import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.model.Stream
import com.shudss00.gigachat.domain.streams.GetAllStreamsUseCase
import com.shudss00.gigachat.domain.streams.GetSubscribedStreamsUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamItem
import com.shudss00.gigachat.presentation.streamlist.listitems.StreamListItem
import com.shudss00.gigachat.presentation.streamlist.listitems.TopicItem
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class StreamListPresenter @Inject constructor(
    private val getAllStreamsUseCase: GetAllStreamsUseCase,
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase
) : RxPresenter<StreamListView>() {

    var currentStreamListType: StreamListType = StreamListType.SUBSCRIBED
        set(value) {
            field = value
            getStreams()
        }
    private val expandedStreamItemTitles = hashMapOf(
        *StreamListType.values().map {
            it to linkedSetOf<String>()
        }.toTypedArray()
    )
    private val disposableContainer = SerialDisposable()
    private val messagesNumberTemporaryStub = 666

    init {
        disposableContainer.disposeOnFinish()
    }

    fun getStreams(expandableStreamItemTitle: String? = null) {
        expandStreamItem(currentStreamListType, expandableStreamItemTitle)
        disposableContainer.set(
            when (currentStreamListType) {
                StreamListType.SUBSCRIBED -> getSubscribedStreamsUseCase()
                StreamListType.ALL_STREAMS -> getAllStreamsUseCase()
            }
                .map { mapStreamsToStreamListItems(currentStreamListType, it) }
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
        )
    }

    private fun expandStreamItem(streamType: StreamListType, streamItemTitle: String?) {
        streamItemTitle?.let {
            if (expandedStreamItemTitles[streamType]!!.remove(streamItemTitle).not()) {
                expandedStreamItemTitles[streamType]!!.add(streamItemTitle)
            }
        }
    }

    private fun mapStreamsToStreamListItems(streamType: StreamListType, streams: List<Stream>): List<StreamListItem> {
        val streamItems = mutableListOf<StreamListItem>()
        streams.forEach { stream ->
            val isStreamItemExpanded = expandedStreamItemTitles[streamType]!!.contains(stream.title)
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
        return streamItems
    }
}