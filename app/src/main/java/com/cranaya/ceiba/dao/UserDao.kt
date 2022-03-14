package com.cranaya.ceiba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cranaya.ceiba.models.UsersBean
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM users_table")
    fun getUser(): Flowable<List<UsersBean>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UsersBean>): Completable

    @Query("SELECT * FROM users_table WHERE name LIKE :searchUser")
    fun searchUser(searchUser: String): Flowable<List<UsersBean>>
}