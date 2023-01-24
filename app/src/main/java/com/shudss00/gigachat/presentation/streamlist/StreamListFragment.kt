package com.shudss00.gigachat.presentation.streamlist

import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.base.MvpFragment
import javax.inject.Inject

class StreamListFragment : MvpFragment<StreamListView, StreamListPresenter>(R.layout.fragment_stream_list), StreamListView {

    @Inject
    override lateinit var presenter: StreamListPresenter
    override var mvpView: StreamListView = this

    override fun initUI() {
        TODO("Not yet implemented")
    }
}