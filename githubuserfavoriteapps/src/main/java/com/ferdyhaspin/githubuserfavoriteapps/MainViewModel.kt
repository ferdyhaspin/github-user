package com.ferdyhaspin.githubuserfavoriteapps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by ferdyhaspin & ilhamelmujib on 22/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
class MainViewModel : ViewModel() {

    private val _user: MutableLiveData<List<User>> = MutableLiveData()
    val user: LiveData<List<User>> get() = _user

    fun loadFavorite() {
        mutableListOf<User>().apply {
            add(User("https://avatars2.githubusercontent.com/u/30462992?v=4", "ferdy"))
            add(User("https://avatars2.githubusercontent.com/u/30462992?v=4", "gans"))
            add(User("https://avatars2.githubusercontent.com/u/30462992?v=4", "sekali"))
            add(User("https://avatars2.githubusercontent.com/u/30462992?v=4", "waw"))

            _user.postValue(this)
        }
    }
}