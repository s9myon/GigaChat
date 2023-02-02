package com.shudss00.gigachat.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.shudss00.gigachat.presentation.base.presenter.Presenter

abstract class MvpFragment<V : MvpView, P : Presenter<V>>(
    @LayoutRes layoutRes: Int
) : Fragment(layoutRes), MvpViewCallback<V, P> {

    private val mvpHelper: MvpHelper<V, P> by lazy { MvpHelper(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpHelper.create()
        initUI()
    }

    override fun onDestroyView() {
        val isFinishing = isRemoving || requireActivity().isFinishing
        mvpHelper.destroy(isFinishing)
        super.onDestroyView()
    }

    fun showToast(@StringRes text: Int) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun initUI()
}