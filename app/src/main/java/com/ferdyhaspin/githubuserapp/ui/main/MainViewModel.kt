package com.ferdyhaspin.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.data.repository.UserRepository
import com.ferdyhaspin.githubuserapp.util.Coroutines
import com.ferdyhaspin.githubuserapp.util.ext.post
import javax.inject.Inject

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

class MainViewModel @Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        Log.d("Test", "initialize..")
    }

    val user = userRepository.searchResponse
    val searchText: LiveData<String> = MutableLiveData()

    val test: LiveData<String> = MutableLiveData()

    fun searchUser(username: String) = Coroutines.io {
        searchText.post(username)
        userRepository.searchUser(username)
    }

    fun setTest(value: String) {
        test.post(value)
    }
}