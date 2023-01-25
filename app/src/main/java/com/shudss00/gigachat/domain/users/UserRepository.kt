package com.shudss00.gigachat.domain.users

import com.shudss00.gigachat.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun getOwnUser(): Single<User>

    fun getAllUsers(): Single<List<User>>
}