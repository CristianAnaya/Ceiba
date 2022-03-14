package com.cranaya.ceiba.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cranaya.ceiba.database.UserDatabase
import com.cranaya.ceiba.models.UsersBean
import com.cranaya.ceiba.repositories.UserListRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class UserListViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = "UserListViewModel"

    private var userListRepository = UserListRepository()
    private val userDatabase = UserDatabase.getUsersDatabase(application)

    fun getUserList(): LiveData<List<UsersBean>>{
        return userListRepository.getUserList()
    }

    fun insertUsers(users: List<UsersBean>): Completable {
        return userDatabase.userDao().insertUsers(users)
    }

    fun loadUsersLocal(): Flowable<List<UsersBean>> {
        return userDatabase.userDao().getUser()
    }

    fun searchUser(searchUser: String): Flowable<List<UsersBean>>{
        return userDatabase.userDao().searchUser(searchUser)
    }
}