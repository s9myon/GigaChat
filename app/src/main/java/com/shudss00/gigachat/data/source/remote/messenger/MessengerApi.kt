package com.shudss00.gigachat.data.source.remote.messenger

import com.shudss00.gigachat.data.model.response.GetMessagesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MessengerApi {

    @GET("messages")
    fun getMessages(
        @Query("anchor") anchor: String = "newest",
        @Query("include_anchor") includeAnchor: Boolean = true,
        @Query("num_before") numBefore: Int = 50,
        @Query("num_after") numAfter: Int = 0,
        @Query("narrow") narrows: String = "[]",
        @Query("client_gravatar") clientGravatar: Boolean = false,
        @Query("apply_markdown") applyMarkdown: Boolean = true
    ): Single<GetMessagesResponse>
}