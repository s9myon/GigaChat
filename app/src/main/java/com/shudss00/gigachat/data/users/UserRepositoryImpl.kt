package com.shudss00.gigachat.data.users

import com.shudss00.gigachat.BuildConfig
import com.shudss00.gigachat.data.source.local.db.users.UserDao
import com.shudss00.gigachat.data.source.remote.users.UserApi
import com.shudss00.gigachat.domain.model.User
import com.shudss00.gigachat.domain.users.UserRepository
import com.shudss00.gigachat.domain.common.OnlineStatus
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : UserRepository {

    override fun observeOwnUser(): Observable<User> {
        return userDao.getUserById(BuildConfig.USER_ID)
            .map { userMapper.mapEntityToModel(it) }
            .toObservable()
    }

    override fun observeUsers(): Observable<List<User>> {
        val disposables = CompositeDisposable()
        return getLocalUsers()
            .doOnSubscribe {
                getRemoteUsers()
                    .flatMapCompletable { saveUsers(it) }
                    .subscribeBy(onError = { Timber.e(it) })
                    .addTo(disposables)
            }
            .doOnDispose { disposables.dispose() }
    }

    override fun getUserByNameOrEmail(searchBy: String): Single<List<User>> {
        return userDao.getUsersByNameOrEmail(searchBy)
            .map { users ->
                users.map { userMapper.mapEntityToModel(it) }
            }
    }

    override fun refreshUsers(): Completable {
        return getRemoteUsers()
            .flatMapCompletable { saveUsers(it) }
    }

    private fun saveUsers(users: List<User>): Completable {
        return userDao.insertUsers(
            *users
                .map { userMapper.mapModelToEntity(it) }
                .toTypedArray()
        )
    }

    private fun getLocalUsers(): Observable<List<User>> {
        return userDao.getAllUsers()
            .map { users ->
                users.map { userMapper.mapEntityToModel(it) }
            }
    }

    private fun getRemoteUsers(): Single<List<User>> {
        return userApi.getAllUsers()
            .flattenAsObservable { it.members }
            .filter { !it.isBot }
            .flatMapSingle { user ->
                userApi.getUserPresence(user.id).map { response ->
                    userMapper.mapDtoToModel(
                        user,
                        OnlineStatus.from(
                            response.presence["aggregated"]!!.status
                        )
                    )
                }
            }
            .toList()
    }
}