package com.cranaya.ceiba.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cranaya.ceiba.R
import com.cranaya.ceiba.databinding.ItemContainterPostsBinding
import com.cranaya.ceiba.databinding.ItemContainterUsersBinding
import com.cranaya.ceiba.models.PostsBean
import com.cranaya.ceiba.models.UsersBean

class PostsAdapter(private var posts: List<PostsBean>): RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var layoutInflater: LayoutInflater?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val itemContainerPosts: ItemContainterPostsBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.item_containter_posts, parent, false
        )

        return PostsViewHolder(itemContainerPosts)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.binPosts(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostsViewHolder(private var itemContainerPosts: ItemContainterPostsBinding):
            RecyclerView.ViewHolder(itemContainerPosts.root){

                fun binPosts(postsBean: PostsBean){
                    itemContainerPosts.postsBean = postsBean
                    itemContainerPosts.executePendingBindings()

                    itemContainerPosts.txtReadMore.setOnClickListener {
                        if (itemContainerPosts.txtReadMore.text.toString() == "Read more") {
                            itemContainerPosts.txtBody.maxLines = Integer.MAX_VALUE
                            itemContainerPosts.txtBody.ellipsize = null
                            itemContainerPosts.txtReadMore.text = "Read less"
                        } else {
                            itemContainerPosts.txtBody.maxLines = 2
                            itemContainerPosts.txtBody.ellipsize =
                                TextUtils.TruncateAt.END
                            itemContainerPosts.txtReadMore.text = "Read more"
                        }
                    }

                }
            }

}