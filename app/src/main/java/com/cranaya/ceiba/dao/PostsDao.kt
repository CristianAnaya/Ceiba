package com.cranaya.ceiba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.UsersBean
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts_table WHERE userId = :userId")
    fun getPosts(userId: Int): Flowable<List<PostsBean>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<PostsBean>): Completable
}