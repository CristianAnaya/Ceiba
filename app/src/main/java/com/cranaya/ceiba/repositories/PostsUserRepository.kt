package com.cranaya.ceiba.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.network.ApiClient
import com.cranaya.ceiba.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsUserRepository {
    private val TAG = "PostsUserRepository"

    private var apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    fun getPostsUser(userId: Int): LiveData<List<PostsBean>> {
        val data = MutableLiveData<List<PostsBean>>()

        apiService.getPostsByUser(userId).enqueue(object : Callback<List<PostsBean>>{
            override fun onResponse(
                call: Call<List<PostsBean>>,
                response: Response<List<PostsBean>>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<List<PostsBean>>, t: Throwable) {

            }

        })
        return data
    }
}