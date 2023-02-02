package com.shudss00.gigachat.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.shudss00.gigachat.presentation.base.presenter.Presenter

abstract class MvpActivity<V: MvpView, P: Presenter<V>>(
    @LayoutRes layoutRes: Int
) : AppCompatActivity(layoutRes), MvpViewCallback<V, P> {

    private val mvpHelper: MvpHelper<V, P> by lazy { MvpHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpHelper.create()
        initUI()
    }

    override fun onDestroy() {
        mvpHelper.destroy(isFinishing)
        super.onDestroy()
    }

    fun showToast(@StringRes text: Int) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    // !!!temporary solution!!!
    abstract fun initUI()
}