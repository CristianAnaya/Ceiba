package com.cranaya.ceiba.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cranaya.ceiba.dao.PostsDao
import com.cranaya.ceiba.dao.UserDao
import com.cranaya.ceiba.models.AddressBean
import com.cranaya.ceiba.models.GeoBean
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.UsersBean
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest: TestCase() {
    // get reference to the LanguageDatabase and LanguageDao class
    private lateinit var db: UserDatabase
    private lateinit var daoUser: UserDao
    private lateinit var daoPosts: PostsDao

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()

        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        daoUser = db.userDao()
        daoPosts = db.postsDao()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is not present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadUser() = runBlocking{
        var list = ArrayList<UsersBean>()
        val geo = GeoBean(0.3,0.4)
        val addressBean = AddressBean("prueba", "prueba suite", "Barranquilla", "39939493",geo)
        list.add(UsersBean(2121,"cristian", "cdanaya11", addressBean))

        daoUser.insertUsers(list)

        val users =  daoUser.getUser()

        assertThat(list.contains(users)).isFalse()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is not equal in the db -- if the item is present then our test cases pass
    @Test
    fun searchUser() = runBlocking{
        var list = ArrayList<UsersBean>()
        val geo = GeoBean(0.3,0.4)
        val addressBean = AddressBean("prueba", "prueba suite", "Barranquilla", "39939493",geo)
        list.add(UsersBean(2121,"cristian", "cdanaya11", addressBean))

        val users =  daoUser.searchUser("cristian")

        assertThat(list).isNotEqualTo(users)
    }


    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is not present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadPosts() = runBlocking{
        var list = ArrayList<PostsBean>()
        list.add(PostsBean(2121,2, "prueba titulo", "prueba del body"))

        daoPosts.insertPosts(list)

        val postsList =  daoPosts.getPosts(2121)

        assertThat(list.contains(postsList)).isFalse()

    }
}