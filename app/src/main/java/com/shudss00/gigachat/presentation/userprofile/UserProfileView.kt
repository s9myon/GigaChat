package com.shudss00.gigachat.presentation.userprofile

import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.presentation.base.MvpView

interface UserProfileView : MvpView {
    fun showUser(user: User)
    fun showError(text: Int)
}