package com.ferdyhaspin.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.data.model.UserDetail
import com.ferdyhaspin.githubuserapp.data.repository.UserRepository
import com.ferdyhaspin.githubuserapp.util.Coroutines
import com.ferdyhaspin.githubuserapp.util.ext.post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ferdyhaspin on 11/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class DetailViewModel @Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user: LiveData<User> = MutableLiveData()
    val userDetail: LiveData<UserDetail> = MutableLiveData()
    val userDetailResponse = userRepository.userDetailResponse
    val followers = userRepository.followersResponse
    val following = userRepository.followingResponse

    val isFavorite = userRepository.isFavorite

    fun setUser(usersItem: User) = Coroutines.io {
        user.post(usersItem)
    }

    fun setUserDetail(usersItem: UserDetail) {
        userDetail.post(usersItem)
    }

    fun getDetail(username: String) = CoroutineScope(Dispatchers.IO).launch {
        launch {
            userRepository.detail(username)
        }
        launch {
            userRepository.getFollowers(username)
        }
        launch {
            userRepository.getFollowing(username)
        }
    }

    fun loadUserFavorite(id: Int?) = Coroutines.io {
        id?.let { userRepository.loadFavoriteById(it) }
    }

    fun addFavorite(user: User) = Coroutines.io {
        userRepository.addFavorite(user)
        loadUserFavorite(user.id)
    }

    fun deleteFavorite(user: User) = Coroutines.io {
        userRepository.deleteFavorite(user)
        loadUserFavorite(user.id)
    }
}