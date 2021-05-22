package com.dicoding.githubuserapp.ui.detail

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.api.util
import com.dicoding.githubuserapp.data.model.User
import com.dicoding.githubuserapp.data.model.UserItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Job
import org.json.JSONObject

class UserDetailActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    var job : Job?= null

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val user  = intent.getSerializableExtra(EXTRA_USER) as UserItem
        downloadDetail(user.username)

        tabLayout()
    }

    fun downloadDetail (username : String) {
        job?.cancel()
        val url = "https://api.github.com/users/$username"
        job = util.download(this, url) {
            try {
                val jsonObject = JSONObject(it)
                val username: String = jsonObject.getString("login")
                val avatar: String = jsonObject.getString("avatar_url")
                val name: String = jsonObject.getString("name")
                val company: String = jsonObject.getString("company")
                val location: String = jsonObject.getString("location")
                val repository: String = jsonObject.getString("public_repos")
                val followers: String = jsonObject.getString("followers")
                val following: String = jsonObject.getString("following")
                callBack(User(avatar, name, username, company, location, repository, followers, following))
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT)
                        .show()
                e.printStackTrace()
            }
        }
    }

    fun callBack (user : User){
        val name: TextView = findViewById(R.id.tv_set_name)
        val userName: TextView = findViewById(R.id.tv_set_user_name)
        val location: TextView = findViewById(R.id.tv_set_location)
        val repository: TextView = findViewById(R.id.tv_set_repository)
        val company: TextView = findViewById(R.id.tv_set_company)
        val followers: TextView = findViewById(R.id.tv_set_followers)
        val following: TextView = findViewById(R.id.tv_set_following)
        val avatar: CircleImageView = findViewById(R.id.img_item_photo)

        name.text = user.name
        userName.text = "Username: ${user.username}"
        location.text = "Location: ${user.location}"
        repository.text = "Repository: ${user.repository}"
        company.text = "Company: ${user.company}"
        followers.text = "${user.followers} followers"
        following.text = "${user.following} following"
        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions())
            .into(avatar)
    }

    fun tabLayout () {
        val user  = intent.getSerializableExtra(EXTRA_USER) as UserItem
        adapter = ViewPagerAdapter(this, user.username)
        val vp = findViewById<ViewPager2>(R.id.view_pager)
        val tabs = findViewById<TabLayout>(R.id.tab_layout)
        val vpTitles = arrayOf("Followers", "Following")
        vp.adapter = adapter
        TabLayoutMediator(tabs, vp) { tab, pos ->
            tab.text = (vpTitles[pos])
        }.attach()
    }
}