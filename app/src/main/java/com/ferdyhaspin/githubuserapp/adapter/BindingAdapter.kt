package com.ferdyhaspin.githubuserapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ferdyhaspin.githubuserapp.util.ext.loadImageCircle

/**
 * Created by ferdyhaspin on 11/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

@BindingAdapter("loadImageCircle")
fun loadImage(image: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        image.loadImageCircle(url)
    }
}

