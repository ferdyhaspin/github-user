package com.ferdyhaspin.githubuserapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by ferdyhaspin on 11/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

class PagerAdapterActivity(
    fragment: FragmentActivity,
    private val fragmentCreators: List<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return fragmentCreators[position]
    }

}