package com.shudss00.gigachat.data.source.local.db.messages

import androidx.room.*
import com.shudss00.gigachat.data.source.local.db.entity.MessageEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages " +
            "WHERE messages.topic_id = :topicId " +
            "ORDER BY timestamp " +
            "LIMIT :limit OFFSET :offset")
    fun readMessagesFromTopic(topicId: Long, limit: Int, offset: Int): Single<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessages(vararg messages: MessageEntity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMessages(vararg messages: MessageEntity): Completable
}