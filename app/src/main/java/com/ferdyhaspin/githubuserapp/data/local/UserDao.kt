package com.ferdyhaspin.githubuserapp.data.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ferdyhaspin.githubuserapp.data.model.User

/**
 * Created by ferdyhaspin & ilhamelmujib on 13/04/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */

@Dao
interface UserDao {

    @Insert
    suspend fun insert(vararg user: User)

    @Delete
    suspend fun delete(vararg user: User)

    @Query("SELECT * FROM user")
    suspend fun selectAll(): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun selectById(id: Int): User?

    @Query("SELECT * FROM user")
    fun provideSelectALl(): Cursor?

    @Query("SELECT * FROM user WHERE id = :id")
    fun provideSelectById(id: Long): Cursor?

    @Query("SELECT * FROM user")
    fun getAllFavorite(): List<User>
}