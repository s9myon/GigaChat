package com.shudss00.gigachat.domain.users

import com.shudss00.gigachat.domain.model.UserItem
import io.reactivex.Single
import javax.inject.Inject

class GetOwnUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Single<UserItem> {
        return userRepository.getOwnUser()
    }
}