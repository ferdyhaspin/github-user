package com.ferdyhaspin.githubuserapp.data.model

/**
 * Created by ferdyhaspin on 09/03/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

class Error(val code: Int, val description: String) {

    constructor(exception: Exception) : this(
        code = -1, description = exception.message ?: ""
    )

}