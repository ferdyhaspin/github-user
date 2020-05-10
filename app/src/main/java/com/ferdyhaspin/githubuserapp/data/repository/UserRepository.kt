package com.ferdyhaspin.githubuserapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.model.Error
import com.ferdyhaspin.githubuserapp.data.model.Resource
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.util.ext.toJson
import com.ferdyhaspin.githubuserapp.util.ext.toObject
import com.google.gson.Gson
import com.throwback.adminkq.utils.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class UserRepository(
    private val context: Context
) {

    val user: LiveData<Resource<User>> = MutableLiveData()

    suspend fun fetchUser() = withContext(Dispatchers.IO) {
        user.post(Resource.Loading())
        try {
            delay(2000L)
            val data = context.resources
                .openRawResource(R.raw.githubuser)
                .bufferedReader()
                .use {
                    it.readText()
                }
            val userData = data.toObject<User>()
            user.post(Resource.Success(userData))
        } catch (e: Exception) {
            e.printStackTrace()
            user.post(Resource.DataError(Error(e)))
        }
    }

}