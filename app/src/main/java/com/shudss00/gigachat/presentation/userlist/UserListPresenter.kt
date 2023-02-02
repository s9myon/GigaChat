package com.shudss00.gigachat.presentation.userlist

import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.users.GetAllUsersUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class UserListPresenter @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : RxPresenter<UserListView>() {

    fun getAllUsers() {
        getAllUsersUseCase()
            .async()
            .subscribeBy(
                onSuccess = {
                    view?.showAllUsers(it)
                },
                onError = {
                    Timber.e(it)
                    view?.showErrorToast(R.string.error_failed_update_data)
                }
            )
            .disposeOnFinish()
    }
}