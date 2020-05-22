package com.ferdyhaspin.githubuserfavoriteapps

import android.net.Uri

/**
 * Created by ferdyhaspin & ilhamelmujib on 22/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
object Utils {

    private const val AUTHORITY = "com.ferdyhaspin.githubuserapp"
    private const val TABLE_NAME = "user"

    val CONTENT_URI: Uri = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()

    const val COLUMN_USERNAME = "login"
    const val COLUMN_IMAGE = "avatarUrl"
}