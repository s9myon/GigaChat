package com.shudss00.gigachat.presentation.userlist

import com.shudss00.gigachat.domain.users.GetAllUsersUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import javax.inject.Inject

class UserListPresenter @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : RxPresenter<UserListView>()