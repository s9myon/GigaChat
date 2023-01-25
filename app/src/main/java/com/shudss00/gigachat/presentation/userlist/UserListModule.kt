package com.shudss00.gigachat.presentation.userlist

import com.shudss00.gigachat.domain.users.GetAllUsersUseCase
import dagger.Module
import dagger.Provides

@Module
class UserListModule {
    @Provides
    fun providePresenter(
        getAllUsersUseCase: GetAllUsersUseCase
    ): UserListPresenter {
        return UserListPresenter(
            getAllUsersUseCase
        )
    }
}