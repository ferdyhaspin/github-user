package com.ferdyhaspin.githubuserapp.data.remote.service

import com.ferdyhaspin.githubuserapp.data.model.SearchResponse
import com.ferdyhaspin.githubuserapp.data.model.User
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

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") id: String
    ): Response<List<User>>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") id: String
    ): Response<List<User>>
}