package com.shudss00.gigachat.data.source.remote.messages

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface MessageApi {

    @GET("messages/{message_id}")
    fun getSingleMessage(
        @Path("message_id") messageId: Long,
        @Query("apply_markdown") applyMarkdown: Boolean = true
    ): Single<GetSingleMessageResponse>

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

    @FormUrlEncoded
    @POST("messages")
    fun sendMessage(
        @Field("type") type: String,
        @Field("to") to: String,
        @Field("topic") topic: String? = null,
        @Field("content") content: String
    ): Completable

    @FormUrlEncoded
    @POST("messages/{message_id}/reactions")
    fun addReaction(
        @Path("message_id") messageId: Long,
        @Field("emoji_name") emojiName: String
    ): Completable

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "messages/{message_id}/reactions", hasBody = true)
    fun deleteReaction(
        @Path("message_id") messageId: Long,
        @Field("emoji_name") emojiName: String
    ): Completable
}