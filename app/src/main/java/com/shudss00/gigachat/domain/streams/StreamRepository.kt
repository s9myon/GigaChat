package com.shudss00.gigachat.domain.streams

import com.shudss00.gigachat.domain.model.Stream
import io.reactivex.Single

interface StreamRepository {

    fun getSubscribedStreams(): Single<List<Stream>>

    fun getAllStreams(): Single<List<Stream>>
}