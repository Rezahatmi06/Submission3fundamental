package com.example.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityDetail : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(URL)
        showTabLayout()

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        user.let {
            if (it != null) {
                viewModel.setDetailUsers(it)
            }
        }

        viewModel.getDetailUsers().observe(this) {
            binding.txtUsername.text = "@" + it.login
            binding.txtName.text = it.name
            binding.txtFollowers.text = it.followers.toString()
            binding.txtFollowing.text = it.following.toString()
            binding.txtCompany.text = it.company
            binding.txtLocation.text = it.location
            Glide.with(this@ActivityDetail)
                .load(it.avatar_url)
                .circleCrop()
                .into(binding.imgReceived)
        }
        showLoading(false)

        var isCheckedFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isCheckedFavorite = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        isCheckedFavorite = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isCheckedFavorite = !isCheckedFavorite
            if (isCheckedFavorite) {
                viewModel.addFavorite(user.toString(), id, avatarUrl.toString())
            } else {
                viewModel.removeFavorite(id)
            }
            binding.toggleFavorite.isChecked = isCheckedFavorite
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, ActivitySetting::class.java)
                startActivity(i)
                return true
            }
            R.id.menu2 -> {
                val j = Intent(this, ActivityFavorite::class.java)
                startActivity(j)
                return true
            }
            else -> return true
        }
    }

    private fun showTabLayout() {
        val username = intent.getStringExtra(USERNAME)
        val bundle = Bundle()
        bundle.putString(USERNAME, username)
        val sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager, lifecycle, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        showLoading(true)
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progresBar1.visibility = View.VISIBLE
        } else {
            binding.progresBar1.visibility = View.GONE
        }
    }

    companion object {
        const val USERNAME = "username"
        const val ID = "id"
        const val URL = "url"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}