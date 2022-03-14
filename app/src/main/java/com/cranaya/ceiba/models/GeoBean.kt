package com.cranaya.ceiba.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

data class GeoBean(
    @SerializedName("lat")
    val geo: Double = 0.0,

    @SerializedName("lng")
    val lng: Double = 0.0
): Serializable

class TypeConverterGeo {
    val gson: Gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): GeoBean? {
        if (data == null) return null
        val listType: Type = object : TypeToken<GeoBean?>() {}.type
        return gson.fromJson<GeoBean?>(data, listType)

    }

    @TypeConverter
    fun someObjectListToString(someObject: GeoBean?): String? {
        return gson.toJson(someObject)
    }
}