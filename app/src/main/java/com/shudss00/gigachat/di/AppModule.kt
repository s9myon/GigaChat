package com.shudss00.gigachat.di

import com.shudss00.gigachat.domain.messages.GetStreamMessagesUseCase
import com.shudss00.gigachat.domain.messages.MessageRepository
import com.shudss00.gigachat.domain.users.GetOwnUserUseCase
import com.shudss00.gigachat.domain.users.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideGetMessagesUseCase(messageRepository: MessageRepository): GetStreamMessagesUseCase {
        return GetStreamMessagesUseCase(messageRepository)
    }

    @Singleton
    @Provides
    fun provideGetOwnUserUseCase(userRepository: UserRepository): GetOwnUserUseCase {
        return GetOwnUserUseCase(userRepository)
    }
}