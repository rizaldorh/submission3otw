package com.dicoding.githubuserapp.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.model.UserItem
import com.dicoding.githubuserapp.ui.detail.UserDetailActivity
import com.dicoding.githubuserapp.ui.detail.UserDetailActivity.Companion.EXTRA_USER
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    internal var users = arrayListOf<UserItem>()
    set(value) {field = value
    notifyDataSetChanged()}

   class ViewHolder internal constructor(val view: View) : RecyclerView.ViewHolder(view) {
        private val txtUserName: TextView = view.findViewById(R.id.tv_item_user_name)
        private val imgPhoto: CircleImageView = view.findViewById(R.id.img_item_photo)

        internal fun bind(user: UserItem) {
            view.setOnClickListener{
                val intent = Intent(view.context, UserDetailActivity::class.java)
                intent.putExtra(EXTRA_USER, user)
                view.context.startActivity(intent)

            }
            txtUserName.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(250, 250))
                .into(imgPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var itemView = holder.itemView
            val viewHolder = ViewHolder(itemView)

            val user = users.get(position)
            viewHolder.bind(user)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}