package com.shudss00.gigachat.data.source.local.db.streams

import androidx.room.*
import com.shudss00.gigachat.data.source.local.db.entity.StreamEntity
import com.shudss00.gigachat.data.source.local.db.entity.TopicEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface StreamDao {

    @Query("SELECT * FROM streams")
    fun readAllStreams(): Observable<List<StreamEntity>>

    @Query("SELECT * FROM" +
            " streams JOIN topics ON streams.id = topics.stream_id" +
            " WHERE streams.title = :title")
    fun readStreamWithTopicsByTitle(title: String): Maybe<Map<StreamEntity, List<TopicEntity>>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStreams(vararg streams: StreamEntity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStreams(vararg streams: StreamEntity): Completable
}