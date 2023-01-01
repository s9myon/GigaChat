package com.shudss00.gigachat.di

import com.shudss00.gigachat.domain.messenger.GetMessagesUseCase
import com.shudss00.gigachat.domain.messenger.MessengerRepository
import com.shudss00.gigachat.domain.user.GetOwnUserUseCase
import com.shudss00.gigachat.domain.user.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideGetMessagesUseCase(messengerRepository: MessengerRepository): GetMessagesUseCase {
        return GetMessagesUseCase(messengerRepository)
    }

    @Singleton
    @Provides
    fun provideGetOwnUserUseCase(userRepository: UserRepository): GetOwnUserUseCase {
        return GetOwnUserUseCase(userRepository)
    }
}