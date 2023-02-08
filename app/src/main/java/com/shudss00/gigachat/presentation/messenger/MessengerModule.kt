package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.domain.messages.*
import dagger.Module
import dagger.Provides

@Module
class MessengerModule {
    @Provides
    fun providePresenter(
        getStreamMessagesUseCase: GetStreamMessagesUseCase,
        getPrivateMessagesUseCase: GetPrivateMessagesUseCase,
        sendStreamMessageUseCase: SendStreamMessageUseCase,
        sendPrivateMessageUseCase: SendPrivateMessageUseCase,
        setReactionToMessageUseCase: SetReactionToMessageUseCase
    ): MessengerPresenter {
        return MessengerPresenter(
            getStreamMessagesUseCase,
            getPrivateMessagesUseCase,
            sendStreamMessageUseCase,
            sendPrivateMessageUseCase,
            setReactionToMessageUseCase
        )
    }
}