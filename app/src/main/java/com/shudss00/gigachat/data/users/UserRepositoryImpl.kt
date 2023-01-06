package com.shudss00.gigachat.data.users

import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.domain.model.UserItem
import com.shudss00.gigachat.domain.users.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override fun getOwnUser(): Single<UserItem> {
        return userApi.getOwnUser().flatMap { user ->
            userApi.getUserPresence(user.id).map { response ->
                UserItem(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    onlineStatus = response.presence["aggregated"]!!.status,
                    avatar = user.avatar
                )
            }
        }
    }
}