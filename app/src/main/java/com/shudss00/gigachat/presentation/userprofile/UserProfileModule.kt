package com.shudss00.gigachat.presentation.userprofile

import com.shudss00.gigachat.domain.users.GetOwnUserUseCase
import dagger.Module
import dagger.Provides

@Module
class UserProfileModule {
    @Provides
    fun providePresenter(getOwnUserUseCase: GetOwnUserUseCase): UserProfilePresenter {
        return UserProfilePresenter(
            getOwnUserUseCase
        )
    }
}