package com.cranaya.ceiba.network

import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.UsersBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    fun getUsers(): Call<List<UsersBean>>

    @GET("posts")
    fun getPostsByUser(@Query("userId") userId: Int): Call<List<PostsBean>>


}