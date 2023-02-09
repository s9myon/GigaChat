package com.shudss00.gigachat.data.streams

import com.shudss00.gigachat.data.source.remote.dto.StreamDto
import com.shudss00.gigachat.data.source.remote.dto.TopicDto
import com.shudss00.gigachat.domain.model.Stream
import com.shudss00.gigachat.domain.model.Topic
import javax.inject.Inject

class StreamMapper @Inject constructor() {

    fun map(from: StreamDto, topics: List<TopicDto>): Stream {
        return Stream(
            id = from.id,
            title = from.title,
            topics = topics.map {
                Topic(title = it.title)
            }
        )
    }
}