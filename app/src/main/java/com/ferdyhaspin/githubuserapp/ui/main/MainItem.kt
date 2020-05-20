package com.ferdyhaspin.githubuserapp.ui.main

import android.view.View
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.databinding.ItemUserBinding
import com.ferdyhaspin.githubuserapp.util.ext.toVisible
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class MainItem(
    private val user: User,
    private val callback: OnClickListener,
    private val isFavoriteItem: Boolean = false,
    private val favoriteCallback: OnFavoriteClick? = null
) : BindableItem<ItemUserBinding>() {

    override fun getLayout() = R.layout.item_user

    override fun bind(viewBinding: ItemUserBinding, position: Int) {
        viewBinding.apply {
            item = user

            val image = rivPhoto
            val name = tvName

            root.setOnClickListener {
                callback.onItemClickListener(image, name, user = user)
            }

            if (isFavoriteItem) {
                viewBinding.btnDelete.apply {
                    toVisible()
                    setOnClickListener {
                        favoriteCallback?.onDeleteClickListener(user)
                    }
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClickListener(vararg view: View, user: User)
    }

    interface OnFavoriteClick {
        fun onDeleteClickListener(user: User)
    }
}