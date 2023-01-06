package com.shudss00.gigachat.di

import com.shudss00.gigachat.data.messages.MessageRepositoryImpl
import com.shudss00.gigachat.data.source.remote.messages.MessageApi
import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.data.users.UserRepositoryImpl
import com.shudss00.gigachat.domain.messages.MessageRepository
import com.shudss00.gigachat.domain.users.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class ImplementationBindingModule {

    @Singleton
    @Binds
    abstract fun bindMessengerRepository(impl: MessageRepositoryImpl): MessageRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    companion object {

        @Singleton
        @Provides
        fun provideMessageApi(retrofit: Retrofit): MessageApi {
            return retrofit.create(MessageApi::class.java)
        }

        @Singleton
        @Provides
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }
    }
}