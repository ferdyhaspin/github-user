package com.ferdyhaspin.githubuserapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyhaspin.githubuserapp.BuildConfig
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.model.Error
import com.ferdyhaspin.githubuserapp.data.model.Resource
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.data.remote.config.SafeApiRequest
import com.ferdyhaspin.githubuserapp.data.remote.config.ServiceGenerator
import com.ferdyhaspin.githubuserapp.data.remote.service.UserService
import com.ferdyhaspin.githubuserapp.util.ext.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class UserRepository @Inject
constructor(
    serviceGenerator: ServiceGenerator,
    private val context: Context
) : SafeApiRequest() {

    val searchResponse: LiveData<Resource<List<User>>> = MutableLiveData()
    val followersResponse: LiveData<Resource<List<User>>> = MutableLiveData()
    val followingResponse: LiveData<Resource<List<User>>> = MutableLiveData()

    private val service = serviceGenerator.createService(
        UserService::class.java,
        BuildConfig.BASE_URL
    )

    suspend fun searchUser(username: String) = withContext(Dispatchers.IO) {
        searchResponse.post(Resource.Loading())
        try {
            val response = apiRequest {
                service.search(username)
            }

            if (response.items.isNotEmpty()) {
                searchResponse.post(Resource.Success(response.items))
            } else {
                val message = context.getString(R.string.no_data)
                searchResponse.post(Resource.DataError(Error(0, message)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            searchResponse.post(Resource.DataError(Error(e)))
        }
    }

    suspend fun getFollowers(username: String) = withContext(Dispatchers.IO) {
        followersResponse.post(Resource.Loading())
        try {
            val response = apiRequest {
                service.getFollowers(username)
            }

            if (response.isNotEmpty()) {
                followersResponse.post(Resource.Success(response))
            } else {
                val message = context.getString(R.string.no_data)
                followersResponse.post(Resource.DataError(Error(0, message)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            followersResponse.post(Resource.DataError(Error(e)))
        }
    }

    suspend fun getFollowing(username: String) = withContext(Dispatchers.IO) {
        followingResponse.post(Resource.Loading())
        try {
            val response = apiRequest {
                service.getFollowing(username)
            }

            if (response.isNotEmpty()) {
                followingResponse.post(Resource.Success(response))
            } else {
                val message = context.getString(R.string.no_data)
                followingResponse.post(Resource.DataError(Error(0, message)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            followingResponse.post(Resource.DataError(Error(e)))
        }
    }

}