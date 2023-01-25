package com.shudss00.gigachat.data.streams

import com.shudss00.gigachat.data.source.remote.streams.StreamApi
import com.shudss00.gigachat.domain.model.Stream
import com.shudss00.gigachat.domain.streams.StreamRepository
import io.reactivex.Single
import javax.inject.Inject

class StreamRepositoryImpl @Inject constructor(
    private val streamApi: StreamApi,
    private val streamMapper: StreamMapper
) : StreamRepository {

    override fun getSubscribedStreams(): Single<List<Stream>> {
        return streamApi.getSubscribedStreams()
            .flattenAsObservable { it.subscriptions }
            .flatMapSingle { stream ->
                streamApi.getTopicsByStreamId(stream.id)
                    .map { streamMapper.map(stream, it.topics) }
            }
            .toList()
    }

    override fun getAllStreams(): Single<List<Stream>> {
        return streamApi.getAllStreams()
            .flattenAsObservable { it.streams }
            .flatMapSingle { stream ->
                streamApi.getTopicsByStreamId(stream.id)
                    .map { streamMapper.map(stream, it.topics) }
            }
            .toList()
    }
}