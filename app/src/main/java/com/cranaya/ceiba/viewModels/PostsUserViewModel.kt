package com.cranaya.ceiba.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cranaya.ceiba.database.UserDatabase
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.UsersBean
import com.cranaya.ceiba.repositories.PostsUserRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class PostsUserViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = "PostsUserViewModel"

    private var postsUserRepository = PostsUserRepository()
    private val userDatabase = UserDatabase.getUsersDatabase(application)

    fun getPostsByUser(userId: Int): LiveData<List<PostsBean>> {
        return postsUserRepository.getPostsUser(userId)
    }

    fun insertPosts(postsBean: List<PostsBean>): Completable {
        return userDatabase.postsDao().insertPosts(postsBean)
    }

    fun loadPostsUserLocal(userId: Int): Flowable<List<PostsBean>> {
        return userDatabase.postsDao().getPosts(userId)
    }

}