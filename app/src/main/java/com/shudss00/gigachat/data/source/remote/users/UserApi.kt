package com.shudss00.gigachat.data.source.remote.users

import com.shudss00.gigachat.data.source.remote.model.UserDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/me")
    fun getOwnUser(): Single<UserDto>

    @GET("users/{user_id}/presence")
    fun getUserPresence(@Path("user_id") userId: Long): Single<GetUserPresenceResponse>

    @GET("realm/presence")
    fun getUsersPresence(): Single<GetAllUsersPresenceResponse>
}