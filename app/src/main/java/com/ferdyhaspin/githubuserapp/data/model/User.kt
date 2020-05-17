package com.ferdyhaspin.githubuserapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

@Parcelize
data class User(
    @SerializedName("gists_url")
    val gistsUrl: String,

    @SerializedName("repos_url")
    val reposUrl: String,

    @SerializedName("following_url")
    val followingUrl: String,

    @SerializedName("starred_url")
    val starredUrl: String,

    val login: String,

    @SerializedName("followers_url")
    val followersUrl: String,

    val type: String,

    val url: String,

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    val score: Double,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("events_url")
    val eventsUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    val id: Int,

    @SerializedName("gravatar_id")
    val gravatarId: String,

    @SerializedName("node_id")
    val nodeId: String,

    @SerializedName("organizations_url")
    val organizationsUrl: String
) : Parcelable
