package com.shudss00.gigachat.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shudss00.gigachat.data.source.local.db.entity.*
import com.shudss00.gigachat.data.source.local.db.messages.MessageDao
import com.shudss00.gigachat.data.source.local.db.streams.StreamDao
import com.shudss00.gigachat.data.source.local.db.users.UserDao

@Database(
    entities = [
        MessageEntity::class,
        ReactionEntity::class,
        StreamEntity::class,
        TopicEntity::class,
        UserEntity::class,
        UserStreamCrossRefEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun streamDao(): StreamDao
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
}