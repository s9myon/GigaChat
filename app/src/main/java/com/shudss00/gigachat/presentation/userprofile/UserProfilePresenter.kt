package com.shudss00.gigachat.presentation.userprofile

import com.shudss00.gigachat.domain.users.GetOwnUserUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import javax.inject.Inject

class UserProfilePresenter @Inject constructor(
    private val getOwnUserUseCase: GetOwnUserUseCase
) : RxPresenter<UserProfileView>()