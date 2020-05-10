package com.ferdyhaspin.githubuserapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

@Parcelize
data class User(
	val users: List<UsersItem>
) : Parcelable

@Parcelize
data class UsersItem(
	val follower: Int,
	val following: Int,
	val name: String,
	val company: String,
	val location: String,
	val avatar: String,
	val repository: Int,
	val username: String
): Parcelable
