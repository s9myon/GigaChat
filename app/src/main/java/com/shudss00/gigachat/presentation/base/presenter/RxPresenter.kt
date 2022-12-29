package com.shudss00.gigachat.presentation.base.presenter

import com.shudss00.gigachat.presentation.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxPresenter<V: MvpView> : BasePresenter<V>() {

    private val disposables = CompositeDisposable()

    override fun detachView(isFinishing: Boolean) {
        if (isFinishing) {
            disposables.dispose()
        }
        super.detachView(isFinishing)
    }

    protected fun removeDisposable(disposable: CompositeDisposable?) {
        disposable?.let {
            disposables.remove(it)
        }
    }

    protected fun Disposable.disposeOnFinish(): Disposable {
        disposables += this
        return this
    }

    protected fun dispose(disposable: Disposable) {
        if (!disposables.remove(disposable)) {
            disposable.dispose()
        }
    }

    private operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        this.add(disposable)
    }
}