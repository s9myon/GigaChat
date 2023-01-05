package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.domain.messenger.GetMessagesUseCase
import dagger.Module
import dagger.Provides

@Module
class MessengerActivityModule {

    @Provides
    fun providePresenter(getMessagesUseCase: GetMessagesUseCase): MessengerPresenter {
        return MessengerPresenter(getMessagesUseCase)
    }
}