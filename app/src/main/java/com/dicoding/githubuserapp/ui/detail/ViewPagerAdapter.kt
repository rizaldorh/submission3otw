package com.dicoding.githubuserapp.ui.detail

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(a : FragmentActivity, val user : String) : FragmentStateAdapter(a) {

    val isFollower = arrayOf(true, false)
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val f = FragmentUser()
        val arg = bundleOf("follow" to isFollower [position], "user" to user)
        f.arguments = arg
        return f
    }
}