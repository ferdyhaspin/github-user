package com.ferdyhaspin.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.data.model.UsersItem
import com.throwback.adminkq.utils.post

/**
 * Created by ferdyhaspin & ilhamelmujib on 11/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
class DetailViewModel : ViewModel() {

    val user: LiveData<UsersItem> = MutableLiveData()

    fun setUser(usersItem: UsersItem) {
        user.post(usersItem)
    }
}