package com.shudss00.gigachat.presentation.streamlist

import com.shudss00.gigachat.domain.streams.GetAllStreamsUseCase
import com.shudss00.gigachat.domain.streams.GetSubscribedStreamsUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import javax.inject.Inject

class StreamListPresenter @Inject constructor(
    private val getAllStreamsUseCase: GetAllStreamsUseCase,
    private val getSubscribedStreamsUseCase: GetSubscribedStreamsUseCase
) : RxPresenter<StreamListView>()