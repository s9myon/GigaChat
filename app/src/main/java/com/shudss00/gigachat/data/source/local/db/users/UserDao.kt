package com.shudss00.gigachat.data.source.local.db.users

import androidx.room.*
import com.shudss00.gigachat.data.source.local.db.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE users.id = :userId")
    fun getUserById(userId: Long): Maybe<UserEntity>

    @Query("SELECT * FROM users " +
            "WHERE :searchBy = '' " +
            "OR users.name LIKE '%' || :searchBy || '%' " +
            "OR users.email LIKE '%'||:searchBy||'%' " +
            "ORDER BY users.name ASC")
    fun getUsersByNameOrEmail(searchBy: String): Single<List<UserEntity>>

    @Query("SELECT * FROM users ORDER BY users.name ASC")
    fun getAllUsers(): Observable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: UserEntity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg users: UserEntity): Completable
}