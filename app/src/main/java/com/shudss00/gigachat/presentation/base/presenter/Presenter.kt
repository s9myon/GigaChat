package com.shudss00.gigachat.presentation.base.presenter

import com.shudss00.gigachat.presentation.base.MvpView

interface Presenter<V : MvpView> {

    fun attachView(view: V)

    fun detachView(isFinishing: Boolean)
}