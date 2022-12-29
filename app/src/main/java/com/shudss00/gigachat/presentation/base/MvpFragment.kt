package com.shudss00.gigachat.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.shudss00.gigachat.presentation.base.presenter.Presenter

abstract class MvpFragment<V : MvpView, P : Presenter<V>>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), MvpViewCallback<V, P> {

    private val mvpHelper: MvpHelper<V, P> by lazy { MvpHelper(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mvpHelper.create()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        val isFinishing = isRemoving || requireActivity().isFinishing
        mvpHelper.destroy(isFinishing)
        super.onDestroyView()
    }
}