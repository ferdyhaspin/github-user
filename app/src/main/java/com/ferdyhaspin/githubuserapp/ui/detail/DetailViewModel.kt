package com.ferdyhaspin.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.util.ext.post
import javax.inject.Inject

/**
 * Created by ferdyhaspin on 11/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class DetailViewModel @Inject
constructor(

) : ViewModel() {

    val user: LiveData<User> = MutableLiveData()

    fun setUser(usersItem: User) {
        user.post(usersItem)
    }
}