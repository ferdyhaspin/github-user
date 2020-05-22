package com.ferdyhaspin.githubuserapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.data.repository.UserRepository
import com.ferdyhaspin.githubuserapp.util.Coroutines
import javax.inject.Inject

/**
 * Created by ferdyhaspin on 21/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class FavoriteViewModel @Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val listFavorite = userRepository.listFavorite

    fun loadFavorite() = Coroutines.io {
        userRepository.loadFavorite()
    }

    fun delete(user: User) = Coroutines.io {
        userRepository.deleteFavorite(user)
        loadFavorite()
    }
}