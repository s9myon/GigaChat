package com.shudss00.gigachat.data.source.local.db.entity

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "topics",
    indices = [Index("title")],
    foreignKeys = [
        ForeignKey(
            entity = StreamEntity::class,
            parentColumns = ["id"],
            childColumns = ["stream_id"],
            onDelete = CASCADE,
            deferred = true
        )
    ]
)
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "stream_id")
    val streamId: Long
)