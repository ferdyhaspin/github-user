package com.ferdyhaspin.githubuserapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ferdyhaspin.githubuserapp.data.model.User

/**
 * Created by ferdyhaspin & ilhamelmujib on 13/04/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
const val DB_NAME = "db_github"

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}