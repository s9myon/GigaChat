package com.shudss00.gigachat.data.source.remote.streams

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface StreamApi {

    @GET("users/me/subscriptions")
    fun getSubscribedStreams(): Single<GetSubscribedStreamsResponse>

    @GET("streams")
    fun getAllStreams(): Single<GetAllStreamsResponse>

    @GET("users/me/{stream_id}/topics")
    fun getTopicsByStreamId(@Path("stream_id") streamId: Long): Single<GetTopicsResponse>
}