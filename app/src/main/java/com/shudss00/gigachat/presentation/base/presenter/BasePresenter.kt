package com.shudss00.gigachat.presentation.base.presenter

import androidx.annotation.CallSuper
import com.shudss00.gigachat.presentation.base.MvpView

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the view that
 * can be accessed from the children classes by view property.
 */
abstract class BasePresenter<V : MvpView> : Presenter<V> {

    var view: V? = null
        private set

    @CallSuper
    override fun attachView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detachView(isFinishing: Boolean) {
        view = null
    }

    fun hasView(): Boolean {
        return view != null
    }
}