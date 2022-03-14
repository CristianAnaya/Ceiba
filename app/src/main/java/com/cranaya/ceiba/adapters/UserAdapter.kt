package com.cranaya.ceiba.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cranaya.ceiba.R
import com.cranaya.ceiba.databinding.ItemContainterUsersBinding
import com.cranaya.ceiba.models.UsersBean
import com.cranaya.ceiba.ui.activities.PostsUsersActivity

class UserAdapter(private var users: List<UsersBean>,private var context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var layoutInflater: LayoutInflater?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val itemContainerUsers: ItemContainterUsersBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.item_containter_users, parent, false
        )

        return UserViewHolder(itemContainerUsers)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binUser(users[position],context)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(private var itemContainerUsers: ItemContainterUsersBinding):
            RecyclerView.ViewHolder(itemContainerUsers.root){



                fun binUser(usersBean: UsersBean, context: Context){
                    itemContainerUsers.userBean = usersBean
                    itemContainerUsers.executePendingBindings()

                    itemContainerUsers.txtSeePosts.setOnClickListener {
                        val intent = Intent(context, PostsUsersActivity::class.java)
                        intent.putExtra("usersBean",usersBean)
                        context.startActivity(intent)
                    }

                }

            }

}