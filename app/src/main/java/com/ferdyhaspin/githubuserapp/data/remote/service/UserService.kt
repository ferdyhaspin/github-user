package com.ferdyhaspin.githubuserapp.data.remote.service

import com.ferdyhaspin.githubuserapp.data.model.SearchResponse
import com.ferdyhaspin.githubuserapp.data.model.User
import com.ferdyhaspin.githubuserapp.data.model.UserDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ferdyhaspin  on 18/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
interface UserService {

    @GET("search/users")
    suspend fun search(
        @Query("q") username: String
    ): Response<SearchResponse>

    @GET("users/{username}")
    suspend fun detail(
        @Path("username") username: String
    ): Response<UserDetail>

    @GET("users/{username}/followers")
    suspend fun followers(
        @Path("username") username: String
    ): Response<List<User>>

    @GET("users/{username}/following")
    suspend fun following(
        @Path("username") username: String
    ): Response<List<User>>
}