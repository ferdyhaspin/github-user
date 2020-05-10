package com.ferdyhaspin.githubuserapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ferdyhaspin.githubuserapp.util.ext.loadLocal

/**
 * Created by ferdyhaspin & ilhamelmujib on 11/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */

@BindingAdapter("imageLocal")
fun setImageLocal(image: ImageView, url: String?) {
    if (!url.isNullOrEmpty()){
        image.loadLocal(url)
    }
}

@BindingAdapter("imageDrawable")
fun setImageDrawable(image: ImageView, url: String?) {
    if (!url.isNullOrEmpty()){
        val context = image.context
        context.apply {
            val data = resources.getIdentifier(
                url,
                "drawable",
                packageName
            )
            image.background = resources.getDrawable(data, null)
        }
    }
}

