package com.dicoding.githubuserapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.api.util
import com.dicoding.githubuserapp.data.model.UserItem
import com.dicoding.githubuserapp.ui.main.UserAdapter
import kotlinx.coroutines.Job
import org.json.JSONArray

class FragmentUser : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    var isFollower = false
    var job : Job?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFollower = arguments?.getBoolean("follow", false) ?: false

        val recyclerView: RecyclerView = view.findViewById(R.id.recycle_view)

        adapter = UserAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        username = arguments?.getString("user") ?: "default"
        getFollower(username)
    }

    fun getFollower(username : String) {
        val pb = requireView().findViewById<ProgressBar>(R.id.loading)
        pb.visibility = View.VISIBLE
        val end = if(isFollower) "followers" else "following"
        val url = "https://api.github.com/users/$username/$end"
        job?.cancel()
        job = util.download(UserDetailViewModel(), url) {
            try {
                val list = arrayListOf<UserItem>()
                val jsonArray = JSONArray(it)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val username: String = jsonObject.getString("login")
                    val avatar: String = jsonObject.getString("avatar_url")
                    list.add(UserItem(username, avatar))
                }
                adapter.users = list
                pb.visibility = View.GONE
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        }
    }
}