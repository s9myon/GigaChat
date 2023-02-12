package com.shudss00.gigachat.domain.users

import com.shudss00.gigachat.domain.model.User
import io.reactivex.Observable
import javax.inject.Inject

class ObserveUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Observable<List<User>> {
        return userRepository.observeUsers()
    }
}