package com.shudss00.gigachat.data.users

import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.domain.users.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override fun getOwnUser(): Single<User> {
        return userApi.getOwnUser().flatMap { user ->
            userApi.getUserPresence(user.id).map { response ->
                User(
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