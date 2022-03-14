package com.cranaya.ceiba.ui.activities

import android.content.Context
import android.view.View
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.cranaya.ceiba.R
import com.cranaya.ceiba.adapters.UserAdapter
import com.cranaya.ceiba.dao.UserDao
import com.cranaya.ceiba.database.UserDatabase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var db: UserDatabase
    private lateinit var daoUser: UserDao

    val LIST_ITEM_IN_TEST = 4

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        daoUser = db.userDao()

    }

    @After
    fun closeDb(){
        db.close()
    }

    /**
     * Select list item, posts by user
     * the user is in view?
     */
    @Test
    fun clickOnButtonAtRow() {
        onView(withId(R.id.recyclerUsers)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>
            (0, ClickOnButtonView()))
    }

    inner class ClickOnButtonView : ViewAction {

        private var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom text view"
        }

        override fun perform(uiController: UiController, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.txtSeePosts))
        }
    }



        /**
         * Select list item, nav to posts by user
         * pressBack
         */

        @Test
        fun testBackNavigationscrollPositionRecyclerView() {

            onView((withId(R.id.recyclerUsers))).perform(
                actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                    0, ClickOnButtonView()
                )
            )
            onView(withId(R.id.txtName)).check(matches(withText("Leanne Graham")))

            pressBack()

            onView(withId(R.id.recyclerUsers)).check(matches(isDisplayed()))

        }


}