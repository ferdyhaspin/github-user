package com.ferdyhaspin.githubuserapp.util.ext

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ferdyhaspin.githubuserapp.R

/**
 * Created by ferdyhaspin on 26/04/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun ImageView.loadImageCircle(url: String) =
    Glide.with(this)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(
                    R.drawable.bg_fill_darkwhite_circle
                )
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        )
        .load(url)
        .into(this)
