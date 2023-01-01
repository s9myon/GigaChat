package com.shudss00.gigachat.di

import com.shudss00.gigachat.data.messenger.MessengerRepositoryImpl
import com.shudss00.gigachat.data.source.remote.messenger.MessengerApi
import com.shudss00.gigachat.data.source.remote.user.UserApi
import com.shudss00.gigachat.data.user.UserRepositoryImpl
import com.shudss00.gigachat.domain.messenger.MessengerRepository
import com.shudss00.gigachat.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class ImplementationBindingModule {

    @Singleton
    @Binds
    abstract fun bindMessengerRepository(impl: MessengerRepositoryImpl): MessengerRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    companion object {

        @Singleton
        @Provides
        fun provideMessageApi(retrofit: Retrofit): MessengerApi {
            return retrofit.create(MessengerApi::class.java)
        }

        @Singleton
        @Provides
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }
    }
}