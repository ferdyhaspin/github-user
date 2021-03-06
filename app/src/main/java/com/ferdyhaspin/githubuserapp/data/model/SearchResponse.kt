package com.ferdyhaspin.githubuserapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ferdyhaspin  on 18/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
@Parcelize
data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    val items: List<User>
) : Parcelable