package com.shudss00.gigachat.presentation.userlist

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.shudss00.gigachat.R
import com.shudss00.gigachat.domain.users.GetUsersBySubstringUseCase
import com.shudss00.gigachat.domain.users.ObserveUsersUseCase
import com.shudss00.gigachat.domain.users.RefreshUsersUseCase
import com.shudss00.gigachat.presentation.base.presenter.RxPresenter
import com.shudss00.gigachat.presentation.extensions.async
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserListPresenter @Inject constructor(
    private val observeUsersUseCase: ObserveUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase,
    private val getUsersBySubstringUseCase: GetUsersBySubstringUseCase
) : RxPresenter<UserListView>() {

    private val searchState: Relay<String> = PublishRelay.create()
    private val currentUserDisposable = SerialDisposable()

    init {
        subscribeOnSearchState()
        currentUserDisposable.disposeOnFinish()
    }

    fun searchUsers(searchBy: String) {
        searchState.accept(searchBy)
    }

    fun observeUsers() {
        currentUserDisposable.set(
            observeUsersUseCase()
                .async()
                .doOnSubscribe {
                    view?.showLoading()
                }
                .subscribeBy(
                    onNext = {
                        if (it.isNotEmpty()) {
                            view?.showAllUsers(it)
                        }
                    },
                    onError = {
                        Timber.e(it)
                        view?.showErrorToast(R.string.error_failed_update_data)
                    }
                )
                .disposeOnFinish()
        )
    }

    fun refreshUsers() {
        refreshUsersUseCase()
            .async()
            .subscribeBy(
                onError = {
                    Timber.e(it)
                    view?.showErrorToast(R.string.error_failed_update_data)
                }
            )
            .disposeOnFinish()
    }

    private fun subscribeOnSearchState() {
        searchState
            .map { it.trim() }
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .switchMapSingle { getUsersBySubstringUseCase(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext(Observable.just(emptyList()))
            .subscribeBy(
                onNext = {
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