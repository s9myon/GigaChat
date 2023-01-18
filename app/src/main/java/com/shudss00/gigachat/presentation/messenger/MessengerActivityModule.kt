package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.domain.messages.*
import dagger.Module
import dagger.Provides

@Module
class MessengerActivityModule {

    @Provides
    fun providePresenter(
        getMessagesUseCase: GetMessagesUseCase,
        sendStreamMessageUseCase: SendStreamMessageUseCase,
        setReactionToMessageUseCase: SetReactionToMessageUseCase
    ): MessengerPresenter {
        return MessengerPresenter(
            getMessagesUseCase,
            sendStreamMessageUseCase,
            setReactionToMessageUseCase
        )
    }
}