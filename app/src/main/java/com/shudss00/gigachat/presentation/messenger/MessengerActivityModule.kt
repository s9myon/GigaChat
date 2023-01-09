package com.shudss00.gigachat.presentation.messenger

import com.shudss00.gigachat.domain.messages.AddReactionUseCase
import com.shudss00.gigachat.domain.messages.DeleteReactionUseCase
import com.shudss00.gigachat.domain.messages.GetMessagesUseCase
import com.shudss00.gigachat.domain.messages.SendStreamMessageUseCase
import dagger.Module
import dagger.Provides

@Module
class MessengerActivityModule {

    @Provides
    fun providePresenter(
        getMessagesUseCase: GetMessagesUseCase,
        sendStreamMessageUseCase: SendStreamMessageUseCase,
        addReactionUseCase: AddReactionUseCase,
        deleteReactionUseCase: DeleteReactionUseCase
    ): MessengerPresenter {
        return MessengerPresenter(
            getMessagesUseCase,
            sendStreamMessageUseCase,
            addReactionUseCase,
            deleteReactionUseCase
        )
    }
}