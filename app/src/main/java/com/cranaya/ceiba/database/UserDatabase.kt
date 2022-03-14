package com.cranaya.ceiba.database

import android.content.Context
import androidx.room.*
import com.cranaya.ceiba.dao.PostsDao
import com.cranaya.ceiba.dao.UserDao
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.TypeConverterAddress
import com.cranaya.ceiba.models.TypeConverterGeo
import com.cranaya.ceiba.models.UsersBean

@Database(entities = [UsersBean::class, PostsBean::class], version = 2, exportSchema = false)
@TypeConverters(TypeConverterAddress::class)
abstract class UserDatabase: RoomDatabase() {

    companion object{
        private var tvShowDatabase: UserDatabase?= null

        @Synchronized
        fun getUsersDatabase(context: Context): UserDatabase{
            if (tvShowDatabase == null){
                tvShowDatabase = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    "users.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }

            return tvShowDatabase!!
        }

    }

    abstract fun userDao(): UserDao

    abstract fun postsDao(): PostsDao

}