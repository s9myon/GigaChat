package com.shudss00.gigachat.domain.users

import com.shudss00.gigachat.domain.model.UserItem
import io.reactivex.Single

interface UserRepository {

    fun getOwnUser(): Single<UserItem>
}