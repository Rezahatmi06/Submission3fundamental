package com.example.githubuserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.FragmentFollowersBinding

class FragmentFollowers : Fragment(R.layout.fragment_followers) {

    private var vbinding: FragmentFollowersBinding? = null
    private val binding get() = vbinding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var user: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vbinding = FragmentFollowersBinding.bind(view)
        user = arguments?.getString(ActivityDetail.USERNAME).toString()
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding?.apply {
            rvUsers.layoutManager = LinearLayoutManager(requireContext())
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(user)
        viewModel.getListFollowers().observe(viewLifecycleOwner){
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vbinding = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding?.progressBarFollowers?.visibility = View.VISIBLE
        }
        else{
            binding?.progressBarFollowers?.visibility = View.GONE
        }
    }
}