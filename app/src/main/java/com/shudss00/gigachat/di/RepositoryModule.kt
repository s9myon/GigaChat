package com.shudss00.gigachat.di

import com.shudss00.gigachat.data.messages.MessageRepositoryImpl
import com.shudss00.gigachat.data.source.local.db.AppDatabase
import com.shudss00.gigachat.data.source.local.db.messages.MessageDao
import com.shudss00.gigachat.data.source.local.db.streams.StreamDao
import com.shudss00.gigachat.data.source.local.db.users.UserDao
import com.shudss00.gigachat.data.source.remote.messages.MessageApi
import com.shudss00.gigachat.data.source.remote.streams.StreamApi
import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.data.streams.StreamRepositoryImpl
import com.shudss00.gigachat.data.users.UserRepositoryImpl
import com.shudss00.gigachat.domain.messages.MessageRepository
import com.shudss00.gigachat.domain.streams.StreamRepository
import com.shudss00.gigachat.domain.users.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMessengerRepository(impl: MessageRepositoryImpl): MessageRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindStreamRepository(impl: StreamRepositoryImpl): StreamRepository

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

        @Singleton
        @Provides
        fun provideStreamApi(retrofit: Retrofit): StreamApi {
            return retrofit.create(StreamApi::class.java)
        }

        @Singleton
        @Provides
        fun provideMessageDao(database: AppDatabase): MessageDao {
            return database.messageDao()
        }

        @Singleton
        @Provides
        fun provideUserDao(database: AppDatabase): UserDao {
            return database.userDao()
        }

        @Singleton
        @Provides
        fun provideStreamDao(database: AppDatabase): StreamDao {
            return database.streamDao()
        }
    }
}