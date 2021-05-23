package com.dicoding.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.api.util
import com.dicoding.githubuserapp.data.model.User
import kotlinx.coroutines.Job
import org.json.JSONObject

class UserDetailViewModel : ViewModel() {
    val user = MutableLiveData<User>()

    var job: Job? = null

    fun downloadDetail(username: String) {
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
                (
                    User(
                        avatar,
                        name,
                        username,
                        company,
                        location,
                        repository,
                        followers,
                        following
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUserDetail(): LiveData<User> {
        return user
    }

}