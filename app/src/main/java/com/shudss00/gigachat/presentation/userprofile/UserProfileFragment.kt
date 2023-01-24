package com.shudss00.gigachat.presentation.userprofile

import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.base.MvpFragment
import javax.inject.Inject

class UserProfileFragment : MvpFragment<UserProfileView, UserProfilePresenter>(R.layout.fragment_user_profile), UserProfileView {

    @Inject
    override lateinit var presenter: UserProfilePresenter
    override val mvpView: UserProfileView = this

    override fun initUI() {
        TODO("Not yet implemented")
    }
}