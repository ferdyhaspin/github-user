package com.ferdyhaspin.githubuserfavoriteapps

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserfavoriteapps.Utils.CONTENT_URI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by ferdyhaspin & ilhamelmujib on 22/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
class MainViewModel : ViewModel() {

    private val _user: MutableLiveData<List<User>> = MutableLiveData()
    val user: LiveData<List<User>> get() = _user

    fun loadFavorite(context: Context) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val cursor = context.contentResolver?.query(CONTENT_URI, null, null, null, null)
            val userList = mutableListOf<User>().apply {
                cursor?.apply {
                    while (moveToNext()) {
                        val username = getString(getColumnIndexOrThrow(Utils.COLUMN_USERNAME))
                        val image = getString(getColumnIndexOrThrow(Utils.COLUMN_IMAGE))
                        add(User(image, username))
                    }
                }
            }
            cursor?.close()
            _user.postValue(userList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}