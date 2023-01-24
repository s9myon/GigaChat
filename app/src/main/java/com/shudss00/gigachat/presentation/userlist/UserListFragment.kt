package com.shudss00.gigachat.presentation.userlist

import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.base.MvpFragment
import javax.inject.Inject

class UserListFragment : MvpFragment<UserListView, UserListPresenter>(R.layout.fragment_user_list), UserListView {

    @Inject
    override lateinit var presenter: UserListPresenter
    override val mvpView: UserListView = this

    override fun initUI() {
        TODO("Not yet implemented")
    }
}