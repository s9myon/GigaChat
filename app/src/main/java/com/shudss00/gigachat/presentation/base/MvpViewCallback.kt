package com.shudss00.gigachat.presentation.base

import com.shudss00.gigachat.presentation.base.presenter.Presenter

interface MvpViewCallback<V: MvpView, P: Presenter<V>> {

    val presenter: P

    val mvpView: V
}