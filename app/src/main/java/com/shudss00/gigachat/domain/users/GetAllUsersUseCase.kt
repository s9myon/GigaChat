package com.shudss00.gigachat.domain.users

import com.shudss00.gigachat.domain.model.User
import io.reactivex.Single
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Single<List<User>> {
        return userRepository.getAllUsers()
    }
}