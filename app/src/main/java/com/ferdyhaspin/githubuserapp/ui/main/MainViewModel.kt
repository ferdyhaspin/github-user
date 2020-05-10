package com.ferdyhaspin.githubuserapp.ui.main

import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.data.repository.UserRepository
import com.ferdyhaspin.githubuserapp.util.Coroutines

/**
 * Created by ferdyhaspin on 10/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.user

    fun getUser() = Coroutines.io {
        userRepository.fetchUser()
    }
}