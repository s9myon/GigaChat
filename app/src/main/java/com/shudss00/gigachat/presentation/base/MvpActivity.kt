package com.shudss00.gigachat.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shudss00.gigachat.presentation.base.presenter.Presenter

abstract class MvpActivity<V: MvpView, P: Presenter<V>> : AppCompatActivity(), MvpViewCallback<V, P> {

    private val mvpHelper: MvpHelper<V, P> by lazy { MvpHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpHelper.create()
    }

    override fun onDestroy() {
        mvpHelper.destroy(isFinishing)
        super.onDestroy()
    }
}