package com.example.githubuserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle, data: Bundle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragmentBundle: Bundle = data
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0->fragment = FragmentFollowers()
            1->fragment = FragmentFollowing()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}