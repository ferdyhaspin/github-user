package com.ferdyhaspin.githubuserapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.data.local.AppDatabase
import com.ferdyhaspin.githubuserapp.data.local.DB_NAME
import com.ferdyhaspin.githubuserapp.data.model.User
import java.util.*

internal class StackRemoteViewsFactory(
    private val mContext: Context
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var database: AppDatabase
    private val mWidgetItems = ArrayList<User>()

    override fun onCreate() {
        val identityToken = Binder.clearCallingIdentity()
        database = Room
            .databaseBuilder(mContext, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .build()
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDataSetChanged() {
        try {
            val userDao = database.userDao()
            mWidgetItems.clear()
            mWidgetItems.addAll(userDao.getAllFavorite())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        database.close()
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        try {
            val bitmap: Bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mWidgetItems[position].avatarUrl)
                .apply(RequestOptions().fitCenter())
                .submit(800, 550)
                .get()
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        val fillInIntent = Intent().apply {
            putExtra(FavoriteWidget.EXTRA_ITEM, mWidgetItems[position])
        }

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}