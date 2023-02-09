package com.shudss00.gigachat.data.source.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shudss00.gigachat.domain.common.OnlineStatus

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["name"]),
        Index(value = ["email"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "status")
    val status: OnlineStatus,
    @ColumnInfo(name = "avatar")
    val avatar: String
)