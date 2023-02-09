package com.shudss00.gigachat.data.source.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "user_stream_relations",
    primaryKeys = ["user_id", "stream_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = CASCADE,
            deferred = true
        ),
        ForeignKey(
            entity = StreamEntity::class,
            parentColumns = ["id"],
            childColumns = ["stream_id"],
            onDelete = CASCADE,
            deferred = true
        )
    ]
)
data class UserStreamCrossRefEntity(
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "stream_id")
    val streamId: Long
)