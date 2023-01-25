package com.shudss00.gigachat.presentation.streamlist

import com.shudss00.gigachat.domain.streams.GetAllStreamsUseCase
import com.shudss00.gigachat.domain.streams.GetSubscribedStreamsUseCase
import dagger.Module
import dagger.Provides

@Module
class StreamListModule {
    @Provides
    fun providePresenter(
        getAllStreamsUseCase: GetAllStreamsUseCase,
        getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase
    ): StreamListPresenter {
        return StreamListPresenter(
            getAllStreamsUseCase,
            getSubscribedStreamsUseCase
        )
    }
}