package com.shudss00.gigachat.presentation.userlist

import com.shudss00.gigachat.domain.users.GetUsersBySubstringUseCase
import com.shudss00.gigachat.domain.users.ObserveUsersUseCase
import com.shudss00.gigachat.domain.users.RefreshUsersUseCase
import dagger.Module
import dagger.Provides

@Module
class UserListModule {
    @Provides
    fun providePresenter(
        observeUsersUseCase: ObserveUsersUseCase,
        refreshUsersUseCase: RefreshUsersUseCase,
        getUsersBySubstringUseCase: GetUsersBySubstringUseCase
    ): UserListPresenter {
        return UserListPresenter(
            observeUsersUseCase,
            refreshUsersUseCase,
            getUsersBySubstringUseCase
        )
    }
}