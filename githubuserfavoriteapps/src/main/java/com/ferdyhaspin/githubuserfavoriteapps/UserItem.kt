package com.ferdyhaspin.githubuserfavoriteapps

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ferdyhaspin.githubuserfavoriteapps.databinding.ItemUserBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by ferdyhaspin & ilhamelmujib on 22/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
class UserItem(
    private val user: User
) : BindableItem<ItemUserBinding>() {

    override fun getLayout() = R.layout.item_user

    override fun bind(viewBinding: ItemUserBinding, position: Int) {
        viewBinding.item = user
        viewBinding.rivPhoto.apply {
            Glide.with(this)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(android.R.color.darker_gray)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .load(user.image)
                .into(this)
        }
    }
}