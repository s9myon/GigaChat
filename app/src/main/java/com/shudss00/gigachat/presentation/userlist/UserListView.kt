package com.shudss00.gigachat.presentation.userlist

import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.presentation.base.MvpView

interface UserListView : MvpView {
    fun showAllUsers(list: List<User>)
    fun showErrorToast(text: Int)
}