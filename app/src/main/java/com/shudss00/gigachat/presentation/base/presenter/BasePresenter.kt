package com.shudss00.gigachat.presentation.base.presenter

import androidx.annotation.CallSuper
import com.shudss00.gigachat.presentation.base.MvpView

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
abstract class BasePresenter<V : MvpView> : Presenter<V> {

    private var _view: V? = null
    val view: V?
        get() = _view

    @CallSuper
    override fun attachView(view: V) {
        this._view = view
    }

    @CallSuper
    override fun detachView(isFinishing: Boolean) {
        _view = null
    }

    fun hasView(): Boolean {
        return view != null
    }
}