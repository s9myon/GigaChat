package com.shudss00.gigachat.domain.users

import com.shudss00.gigachat.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {

    fun observeOwnUser(): Observable<User>

    fun observeUsers(): Observable<List<User>>

    fun getUserByNameOrEmail(searchBy: String): Single<List<User>>

    fun refreshUsers(): Completable
}