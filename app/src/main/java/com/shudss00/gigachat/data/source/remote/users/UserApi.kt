package com.shudss00.gigachat.data.source.remote.users

import com.shudss00.gigachat.data.source.remote.dto.UserDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("users/me")
    fun getOwnUser(): Single<UserDto>

    @GET("users")
    fun getAllUsers(
        @Query("client_gravatar") clientGravatar: Boolean = false
    ): Single<GetAllUsersResponse>

    @GET("users/{user_id}/presence")
    fun getUserPresence(@Path("user_id") userId: Long): Single<GetUserPresenceResponse>
}