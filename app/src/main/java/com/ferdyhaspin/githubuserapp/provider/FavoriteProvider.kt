package com.ferdyhaspin.githubuserapp.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.ferdyhaspin.githubuserapp.data.local.AppDatabase
import com.ferdyhaspin.githubuserapp.data.local.DB_NAME
import com.ferdyhaspin.githubuserapp.data.local.UserDao

/**
 * Created by ferdyhaspin & ilhamelmujib on 21/05/20.
 * Copyright (c) 2020 Bank Syariah Mandiri - Super Apps All rights reserved.
 */
class FavoriteProvider : ContentProvider() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    companion object {
        private const val DB_TABLE = "user"
        private const val AUTHORITY = "com.ferdyhaspin.githubuserapp"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        private const val CODE_FAV_DIR = 1
        private const val CODE_FAV_ITEM = 2

        init {
            uriMatcher.addURI(AUTHORITY, DB_TABLE, CODE_FAV_DIR)
            uriMatcher.addURI(AUTHORITY, "$DB_TABLE/#", CODE_FAV_ITEM)
        }
    }

    override fun onCreate(): Boolean {
        db = Room
            .databaseBuilder(context as Context, AppDatabase::class.java, DB_NAME)
            .build()
        userDao = db.userDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code: Int = uriMatcher.match(uri)
        return if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM) {
            context?.let { context ->
                val cursor = if (code == CODE_FAV_DIR) {
                    userDao.provideSelectALl()
                } else {
                    userDao.provideSelectById(ContentUris.parseId(uri))
                }
                cursor?.setNotificationUri(context.contentResolver, uri)
                cursor
            } ?: run {
                null
            }

        } else {
            throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun getType(uri: Uri): String? = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) = 0


}
