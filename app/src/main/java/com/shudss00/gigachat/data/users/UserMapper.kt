package com.shudss00.gigachat.data.users

import com.shudss00.gigachat.data.source.local.db.entity.UserEntity
import com.shudss00.gigachat.data.source.remote.dto.UserDto
import com.shudss00.gigachat.domain.common.OnlineStatus
import com.shudss00.gigachat.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapEntityToModel(from: UserEntity): User {
        return User(
            id = from.id,
            name = from.name,
            email = from.email,
            avatar = from.avatar,
            status = from.status
        )
    }

    fun mapModelToEntity(from: User): UserEntity {
        return UserEntity(
            id = from.id,
            name = from.name,
            email = from.email,
            avatar = from.avatar,
            status = from.status
        )
    }

    fun mapDtoToModel(from: UserDto, status: OnlineStatus): User {
        return User(
            id = from.id,
            name = from.name,
            email = from.email,
            avatar = from.avatar,
            status = status
        )
    }

    fun mapDtoToEntity(from: UserDto, status: OnlineStatus): UserEntity {
        return UserEntity(
            id = from.id,
            name = from.name,
            email = from.email,
            avatar = from.avatar,
            status = status
        )
    }
}