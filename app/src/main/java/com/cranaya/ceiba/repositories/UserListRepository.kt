package com.cranaya.ceiba.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cranaya.ceiba.models.UsersBean
import com.cranaya.ceiba.network.ApiClient.Companion.getRetrofit
import com.cranaya.ceiba.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListRepository {
    private val TAG = "UserListRepository"
    private var apiService: ApiService = getRetrofit().create(ApiService::class.java)

    fun getUserList(): LiveData<List<UsersBean>>{
        val data = MutableLiveData<List<UsersBean>>()
        apiService.getUsers().enqueue(object : Callback<List<UsersBean>> {
            override fun onResponse(
                call: Call<List<UsersBean>>,
                response: Response<List<UsersBean>>
            ) {
                data.value = response.body()

            }

            override fun onFailure(call: Call<List<UsersBean>>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)
            }
        })
        return data
    }
}