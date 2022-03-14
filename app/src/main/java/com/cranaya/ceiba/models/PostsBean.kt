package com.cranaya.ceiba.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts_table")
class PostsBean(
    @SerializedName("userId")
    var userId: Int = 0,

    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("body")
    var body: String = ""
)