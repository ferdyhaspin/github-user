package com.ferdyhaspin.githubuserapp.ui.main

import android.view.View
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.databinding.ItemUserBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class MainItem(
    private val user: User,
    private val callback: OnClickListener
) : BindableItem<ItemUserBinding>() {

    override fun getLayout() = R.layout.item_user

    override fun bind(viewBinding: ItemUserBinding, position: Int) {
        viewBinding.item = user

        val image = viewBinding.rivPhoto
        val name = viewBinding.tvName

        viewBinding.root.setOnClickListener {
            callback.onItemClickListener(image, name, user = user)
        }
    }

    interface OnClickListener {
        fun onItemClickListener(vararg view: View, user: User)
    }
}