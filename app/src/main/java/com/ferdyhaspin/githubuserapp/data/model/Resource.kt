package com.ferdyhaspin.githubuserapp.data.model


/**
 * Created by ferdyhaspin on 09/03/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
sealed class Resource<T>(
    val data: T? = null,
    val error: Error? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class DataError<T>(error: Error) : Resource<T>(error = error)
}