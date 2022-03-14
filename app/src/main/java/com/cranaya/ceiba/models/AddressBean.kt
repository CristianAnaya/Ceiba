package com.cranaya.ceiba.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

data class AddressBean(
    @SerializedName("street")
    val street: String = "",

    @SerializedName("suite")
    val suite: String = "",

    @SerializedName("city")
    val city: String = "",

    @SerializedName("zipcode")
    val zipCode: String = "",

    @SerializedName("geo")
    val geo: GeoBean?

): Serializable

class TypeConverterAddress{
    val gson: Gson = Gson()

        @TypeConverter
        fun stringToSomeObjectList(data: String?): AddressBean?{
            if (data == null) return null
            val listType: Type = object: TypeToken<AddressBean?>(){}.type
            return gson.fromJson<AddressBean?>(data,listType)

            }

        @TypeConverter
        fun someObjectListToString(someObject: AddressBean?): String?{
            return gson.toJson(someObject)
        }
}
