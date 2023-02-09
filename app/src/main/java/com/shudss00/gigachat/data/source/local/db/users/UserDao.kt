package com.shudss00.gigachat.data.source.local.db.users

import androidx.room.*
import com.shudss00.gigachat.data.source.local.db.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE users.id = :userId")
    fun readUserById(userId: Long): Observable<UserEntity>

    @Query("SELECT * FROM users" +
            " WHERE users.name LIKE '%'||:searchBy||'%'" +
            " OR users.email LIKE '%'||:searchBy||'%'")
    fun readUsersByNameOrEmail(searchBy: String): Maybe<List<UserEntity>>

    @Query("SELECT * FROM users")
    fun readAllUsers(): Observable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(vararg users: UserEntity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg users: UserEntity): Completable
}