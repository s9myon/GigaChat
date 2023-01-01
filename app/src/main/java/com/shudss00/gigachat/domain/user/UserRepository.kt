package com.shudss00.gigachat.domain.user

import com.shudss00.gigachat.domain.model.UserItem
import io.reactivex.Single

interface UserRepository {

    fun getOwnUser(): Single<UserItem>
}