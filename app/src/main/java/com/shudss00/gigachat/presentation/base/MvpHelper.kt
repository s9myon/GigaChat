package com.shudss00.gigachat.presentation.base

import com.shudss00.gigachat.presentation.base.presenter.Presenter

class MvpHelper<V: MvpView, P: Presenter<V>>(
    private val callback: MvpViewCallback<V, P>
) {

    private lateinit var presenter: Presenter<V>

    fun create() {
        presenter = callback.presenter
        presenter.attachView(callback.mvpView)
    }

    fun destroy(isFinishing: Boolean) {
        presenter.detachView(isFinishing)
    }
}