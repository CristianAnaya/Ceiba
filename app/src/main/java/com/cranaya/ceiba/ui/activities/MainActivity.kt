package com.cranaya.ceiba.ui.activities

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cranaya.ceiba.R
import com.cranaya.ceiba.adapters.UserAdapter
import com.cranaya.ceiba.databinding.ActivityMainBinding
import com.cranaya.ceiba.models.UsersBean
import com.cranaya.ceiba.viewModels.UserListViewModel
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var dialog: AlertDialog
    private lateinit var activityMainBinding: ActivityMainBinding
    private var viewModel: UserListViewModel?= null
    private lateinit var userAdapter: UserAdapter
    private val userList = ArrayList<UsersBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        doInitialization()
    }

    private fun doInitialization() {
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        userAdapter = UserAdapter(userList,this)
        activityMainBinding.recyclerUsers.adapter = userAdapter
        dialog = SpotsDialog.Builder().setContext(this).setMessage("Cargando...").setCancelable(false).build()

        getUserListLocal()

        searchUser()
    }

    private fun searchUser() {
        activityMainBinding.edtSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(query: Editable?) {
                val searchQuery = "%$query%"
                if (query.toString().trim().isNotEmpty()){
                    loadUser(searchQuery)
                }else {
                    userList.clear()
                    getUserListLocal()
                }
            }

        })
    }

    private fun loadUser(query: String) {
        showProgress()
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(viewModel!!.searchUser(query).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { users ->
                if (users.isEmpty()) {
                    hideProgress()
                    userList.clear()
                    userAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show()
                } else {
                    hideProgress()
                    userList.clear()
                    userAdapter.notifyDataSetChanged()
                    val oldCount = users.size
                    userList.addAll(users)
                    userAdapter.notifyItemRangeInserted(oldCount, userList.size)
                }
                compositeDisposable.dispose()
            }
        )
    }

    /**
     * Detect if there is info saved in the database
     */
    private fun getUserListLocal() {
        dialog.show()
        showProgress()
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(viewModel!!.loadUsersLocal().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { users ->
                if (users.isEmpty()) {
                    //if the list is empty request web service
                    requestUserList()
                }else{
                    dialog.dismiss()
                    hideProgress()
                    userList.addAll(users)
                    userAdapter.notifyDataSetChanged()
                }
                compositeDisposable.dispose()
            }
        )
    }

    private fun requestUserList(){
        viewModel!!.getUserList().observe(this, { users ->
            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(viewModel?.insertUsers(users) // insert info user to user_table
            !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    dialog.dismiss()
                    hideProgress()
                    compositeDisposable.dispose()
                }
            )
            userList.addAll(users)
            userAdapter.notifyDataSetChanged()
        })
    }

    private fun hideProgress() {
        activityMainBinding.shimmer.stopShimmer()
        activityMainBinding.shimmer.visibility = View.GONE
        activityMainBinding.recyclerUsers.visibility = View.VISIBLE
    }

    private fun showProgress() {
        activityMainBinding.shimmer.startShimmer()
        activityMainBinding.shimmer.visibility = View.VISIBLE
        activityMainBinding.recyclerUsers.visibility = View.GONE
    }
}