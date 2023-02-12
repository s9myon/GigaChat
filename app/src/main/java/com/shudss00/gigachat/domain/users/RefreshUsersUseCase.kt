package com.shudss00.gigachat.domain.users

import io.reactivex.Completable
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Completable {
        return userRepository.refreshUsers()
    }
}