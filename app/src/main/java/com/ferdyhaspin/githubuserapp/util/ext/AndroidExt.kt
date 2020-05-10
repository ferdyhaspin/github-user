package com.ferdyhaspin.githubuserapp.util.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
/**
 * Created by ferdyhaspin on 26/04/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

inline fun <reified T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.toObject(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T> String.toObjectList(): List<T> =
    Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)