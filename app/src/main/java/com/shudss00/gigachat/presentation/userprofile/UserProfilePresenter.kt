package com.shudss00.gigachat.presentation.userprofile

import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.users.GetOwnUserUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class UserProfilePresenter @Inject constructor(
    private val getOwnUserUseCase: GetOwnUserUseCase
) : RxPresenter<UserProfileView>() {

    fun getOwnUser() {
        getOwnUserUseCase()
            .async()
            .subscribeBy(
                onSuccess = {
                    view?.showUser(it)
                },
                onError = {
                    Timber.e(it)
                    view?.showError(R.string.error_failed_update_data)
                }
            )
            .disposeOnFinish()
    }
}