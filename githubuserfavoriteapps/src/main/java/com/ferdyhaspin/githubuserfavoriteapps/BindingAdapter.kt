package com.ferdyhaspin.githubuserfavoriteapps

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by ferdyhaspin & ilhamelmujib on 22/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
class BindingAdapter {

    @BindingAdapter("loadImageCircle")
    fun loadImage(image: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {

        }
    }
}