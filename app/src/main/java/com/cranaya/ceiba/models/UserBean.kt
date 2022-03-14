package com.cranaya.ceiba.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "users_table")
data class UsersBean(

    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("address")
    val address: AddressBean?,

    @SerializedName("phone")
    val phone: String = ""



): Serializable


