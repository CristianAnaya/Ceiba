package com.cranaya.ceiba.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cranaya.ceiba.R
import com.cranaya.ceiba.adapters.PostsAdapter
import com.cranaya.ceiba.adapters.UserAdapter
import com.cranaya.ceiba.databinding.ActivityPostsUsersBinding
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.UsersBean
import com.cranaya.ceiba.viewModels.PostsUserViewModel
import com.cranaya.ceiba.viewModels.UserListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class PostsUsersActivity : AppCompatActivity() {

    private lateinit var activityPostUserActivity: ActivityPostsUsersBinding
    private var viewModel: PostsUserViewModel?= null
    private lateinit var postsAdapter: PostsAdapter
    private val postsList = ArrayList<PostsBean>()
    private lateinit var userBean: UsersBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostUserActivity = DataBindingUtil.setContentView(this, R.layout.activity_posts_users)

        doInitialization()
    }

    private fun doInitialization() {
        userBean = intent.getSerializableExtra("usersBean") as UsersBean

        viewModel = ViewModelProvider(this).get(PostsUserViewModel::class.java)
        postsAdapter = PostsAdapter(postsList)
        activityPostUserActivity.recyclerPosts.adapter = postsAdapter

        activityPostUserActivity.imgBack.setOnClickListener {
            onBackPressed()
        }
        getPostsLocal()
    }

    /**
     * Detect if there is info saved in the database
     */
    private fun getPostsLocal() {
        showProgress()
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(viewModel!!.loadPostsUserLocal(userBean.id).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { posts ->
                if (posts.isEmpty()) {
                    //if the list is empty request web service
                    requestPostsList()
                }else{
                    hideProgress()
                    postsList.addAll(posts)
                    postsAdapter.notifyDataSetChanged()
                }
                compositeDisposable.dispose()
            }
        )
    }

    private fun requestPostsList() {
        viewModel!!.getPostsByUser(userBean.id).observe(this, { posts ->
            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(viewModel?.insertPosts(posts)
            !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    hideProgress()
                    postsList.addAll(posts)
                    postsAdapter.notifyDataSetChanged()
                    compositeDisposable.dispose()
                }
            )

        })
    }


    private fun hideProgress() {
        activityPostUserActivity.shimmer.stopShimmer()
        activityPostUserActivity.shimmer.visibility = View.GONE
        activityPostUserActivity.recyclerPosts.visibility = View.VISIBLE
    }

    private fun showProgress() {
        activityPostUserActivity.shimmer.startShimmer()
        activityPostUserActivity.shimmer.visibility = View.VISIBLE
        activityPostUserActivity.recyclerPosts.visibility = View.GONE
    }
}